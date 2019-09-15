package cn.itcast.taxi

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.TimeUnit

import com.esri.core.geometry.{GeometryEngine, Point, SpatialReference}
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

import scala.io.Source

object TaxiAnalysisRunner {
def main(args: Array[String]): Unit = {

  // 1.创建SparhSession
  val spark = SparkSession.builder()
    .appName("taxi")
    .master("local[6]")
    .getOrCreate()

  // 导入隐式转换
  import spark.implicits._

  // 导入函数
  import org.apache.spark.sql.functions._

  // 3.数据读取
  val taxiRaw: DataFrame = spark.read
    .option("header", true)
    .csv("D:/develop/idea_workspace/taxi/dataset/half_trip.csv")

  //    taxiRaw.show()
  //    taxiRaw.printSchema() // 打印数据结构

  // 4.转换操作
  val taxiParsed = taxiRaw.rdd.map(safe(parse))
//  val taxiParsed = taxiRaw.rdd.map(parse)

  // 可以通过如下方式来过滤所有异常的row.
//  val result = taxiParsed.filter(e => e.isRight)
//    .map(e => e.right.get._1)

     val taxiGood: Dataset[Trip] = taxiParsed.map(either => either.left.get).toDS()
//  val taxiGood: Dataset[Trip] = taxiParsed.toDS()

  // 5.绘制时长直方图
  // 5.1 编写UDF完成市场计算, 将毫秒转为小时单位
  val hours = (pickUpTime : Long , dropOffTime : Long) => {
    val duration = dropOffTime - pickUpTime

    // 时间单位转换标准表达式: HOURS(小时) -> MILLISECONDS(毫秒) ; duration为时间值;
    val hours = TimeUnit.HOURS.convert(duration , TimeUnit.MILLISECONDS)
    hours
  }
  val hoursUDF = udf(hours)
  // 5.进行统计
  taxiGood.groupBy(hoursUDF($"pickUpTime" , $"dropOffTime") as "duration")
    .count()    // 计数
    .sort("duration")
    .show()

  // 6.根据直方图的显示, 查看数据分布后, 剪除反常数据
  // 将自定义的hours函数注册到 udf 上,用于对其结果实行查询;
  spark.udf.register("hours" , hours)
  // 对taxiGood中的hour数据进行筛选: 0~3之间的
  val taxiClean: Dataset[Trip] = taxiGood.where("hours(pickUpTime , dropOffTime) BETWEEN 0 AND 3" )
  taxiClean.show()

  // 7.增加行政区信息
  // 7.1 读取数据集
  val geoJson = Source.fromFile("D:\\develop\\idea_workspace\\taxi\\dataset\\nyc-borough-boundaries-polygon.geojson").mkString
  // JSON -> Obj
  val featureCollection = FeatureExtraction.parseJson(geoJson)

  // 7.2 排序   拿到经纬度, 遍历features 搜索其所在的行政区, 行政区越大命中的几率就越高, 减少遍历次数  "boroughCode": 行政区编号
  val sortedFeatures = featureCollection.features.sortBy(feature => {
    (feature.properties("boroughCode") , - feature.getGeometry().calculateArea2D())   // calculateArea2D() 计算平面上的面积
  })

  // 7.3 广播   发给每个Excuter
  val featuresBC = spark.sparkContext.broadcast(sortedFeatures)

  // 7.4 UDF创建, 完成功能  x , y 经纬度
  val boroughLookUp = (x : Double, y : Double ) => {
        // 7.4.1 搜索经纬度所在的行政区
        val featureHit = featuresBC.value.find(feature => {
          GeometryEngine.contains( feature.getGeometry() , new Point(x, y) , SpatialReference.create(4326) )
        })
        // 7.4.2 转为行政区信息
        val borough = featureHit.map(feature => feature.properties("borough")).getOrElse("NA")
        borough
  }

//  // 7.5 统计信息 (行政区点个数)
//  val boroughUDF = udf(boroughLookUp)
//  taxiClean.groupBy(boroughUDF('dropOffX , 'dropOffY))
//    .count()
//    .show()

  // 8.1 过滤没有经纬度的数据
  // 8.2 会话分析
  val sessions = taxiClean.where("dropOffX != 0 and dropOffY != 0 and pickUpX != 0 and pickUpY != 0")
    .repartition('license)
    .sortWithinPartitions('license , 'pickUpTime)   // 排序

  // 8.3 求得时间差
  def boroughDuration(t1 : Trip , t2 : Trip) : (String , Double) = {
    val borough = boroughLookUp(t1.dropOffX , t1.dropOffY)
    val duration = (t2.pickUpTime - t1.dropOffTime) / 1000.0
    (borough , duration)
  }

  val boroughtDuration = sessions.mapPartitions(trips => {
    val viter = trips.sliding(2)
      .filter(_.size == 2)
      .filter(p => p.head.license == p.last.license)
    viter.map(p => boroughDuration(p.head , p.last))
  }).toDF("borough" , "seconds")

  boroughtDuration.where("seconds > 0")
    .groupBy("borough")
    .agg(avg('seconds) , stddev('seconds))          // avg() 平均数  stddev() 标准差
    .show()
}


// 作用就是封装parse 方法, 捕获异常 由于上面map方法括号里面是函数表达式, 所以封装parse 方法
  //  返回的结果也必须是函数表达式, 如下: Either[R , (P , Exception)]
  def safe[P , R](f : P => R) : P => Either[R , (P , Exception)] = {

    // 创建一个函数顺便序列化(方便相互转换操作)
    new Function[P , Either[R , (P , Exception)]] with Serializable{

      // 目的就是创建方法调用参数, 做判断
      override def apply(param : P) : Either[R , (P , Exception)] = {
        try{
          Left( f(param) )
        }catch{
          case e : Exception => Right( (param , e) )
        }
      }
    }
  }



  // Row -> trip
  def parse(row : Row) : Trip = {

    val richRow = new RichRow(row)
    val license = richRow.getAs[String]("hack_license").orNull    // orNull 方法:前面的结果为空则返回None.
    val pickUpTime = parseTime(richRow , "pickup_datetime")
    val dropOffTime = parseTime(richRow , "dropoff_datetime")
    val pickUpX = parseLcation(richRow , "pickup_longitude")
    val pickUpY = parseLcation(richRow , "pickup_latitude")
    val dropOffX = parseLcation(richRow , "dropoff_longitude")
    val dropOffY = parseLcation(richRow , "dropoff_latitude")

  val trip = Trip(license , pickUpTime , dropOffTime , pickUpX , pickUpY , dropOffX , dropOffY)
    trip
  }

//  Option 代表某个方法,结果有可能为空, 使得方法调用除必须处理为null的情况
//  Option 对象本身提供了一些对于null的处理支持

  def parseTime(row : RichRow , field : String) : Long = {
    //   1.表示出来时间类型的格式 SimpleDateFormat
    val pattern = "yyyy-MM-dd HH:mm:ss"
    val formatter = new SimpleDateFormat(pattern , Locale.ENGLISH)

    // 2.执行转换, 获取data 对象, getTime 获取时间戳
    val time : Option[String] = row.getAs[String](field)
    val timel: Option[Long] = time.map(time => formatter.parse(time).getTime)
    timel.getOrElse(0L)
  }

  def parseLcation(row : RichRow , field : String) : Double = {
  // 1.获取数据
    val location = row.getAs[String](field)
  // 2.转换数据
    val locationOption = location.map( loc => loc.toDouble )
    locationOption.getOrElse(0.0D)
  }

}

// DataFrame 中的Rom 的包装类型, 主要为了包装getAs 方法
class RichRow(row : Row) {

  // 为了返回Option提醒外面处理空值, 提供处理方式
  def getAs[T](field : String) : Option[T] ={
    val value = row.getAs[String](field)
    // 1.判断row.getAs 是否为空, row 中 对应的field 是否为空
    if(row.isNullAt(row.fieldIndex(field))){

      // 2.为null 则返回None
      None
    }else{
      // 3.不为空 则返回some
      Some(row.getAs[T](field))
    }
  }
}

  // 一个trip 代表一行数据
case class Trip(
            license : String,
            pickUpTime : Long,
            dropOffTime : Long,
            pickUpX : Double,
            pickUpY : Double,
            dropOffX : Double,
            dropOffY : Double
               )