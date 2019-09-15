package cn.itcast.spark.sql


import org.apache.spark.sql.types.{_}
import org.apache.spark.sql.SparkSession
import org.junit.Test

class NullProcessor {

  // 1. 创建SparkSession 对象
  val spark = SparkSession.builder()
    .appName("NullAndNan")
    .master("local[6]")
    .getOrCreate()

  @Test
  def nullAndNaN() : Unit = {

    // 2.导入数据集
    // 3.读取数据集
    // 方式1>. 通过Spark-csv 自动的推断类型来读取
//    val read1 = spark.read
//      .option("header" , true)
//      .option("inferSchema" , true)     // 这种 "inferSchma" 推断方式,会在有数字的时候将 NaN 推断为字符串;不可取;
//      .csv("dataset/Beijing_pm5/beijingpm_with_nan.csv")

    // 方式2>. 直接读取字符串, 在后续的操作中使用 map 算子转换类型
    // spark.read.csv().map(x => x.....)

    // 方式3>. 指定Schema, 不要自动推断
    val schema = StructType(
      List(
       StructField("id" , LongType),
        StructField("year" , IntegerType),
        StructField("month" , IntegerType),
        StructField("day" , IntegerType),
        StructField("hour" , IntegerType),
        StructField("season" , IntegerType),
        StructField("pm" , DoubleType)

      )
    )

    val sourceDF = spark.read
      .option("header" , value = true)
      .schema(schema)
      .csv("dataset/Beijing_pm5/beijingpm_with_nan.csv")

    sourceDF.show()

    // 4.丢弃
    // 2019 , 12 , 12 , 60, NaN
    // 规则:
    // 1.any , 只要有一个NaN就丢弃
    sourceDF.na.drop("any").show()   // 简化写法下面: drop() 方法默认就是any;
    sourceDF.na.drop().show()

    // 2.all , 所有数据都是NaN的行才丢弃
    sourceDF.na.drop("all").show()

    // 3.某些列的规则
    sourceDF.na.drop("any" , List("year" , "month" , "day" , "hour")).show()

    // 5.填充
    // 规则
    // 1.针对所有数据进行默认值填充
    sourceDF.na.fill(0).show()

    // 2.针对特定列填充
    sourceDF.na.fill(0 , List("year" , "month")).show()
  }

  @Test
  def strProcessor() : Unit = {
    // 读取数据集
    val sourceDF = spark.read
      .option("header" , value = true)
      .option("inferSchema" , value = true)
      .csv("dataset/BeijingPM20100101_20151231.csv")

    sourceDF.show()

    // 导入隐式转换
    import spark.implicits._

    // 1.丢弃
    sourceDF.where('PM_Dongsi !== "NA").show()

    // 2.替换
    // sql 语言: select name , age , case
    // when ... then ...
    // when ... then ...
    // else ...
    import org.apache.spark.sql.functions._

    sourceDF.select(
      'No as "id", 'year , 'month , 'day , 'hour , 'season ,
      when('PM_Dongsi === "NA" , Double.NaN)    // 当PM_Dongsi 的值为 NA 时, 替换值为Double类型的 NaN ,
        .otherwise('PM_Dongsi cast DoubleType)     // 如果不是NA 时, 则把值转成Double类型
        .as("pm")         // 再起一个别名 pm
    ).show(50)

    // replace 该方法: 原类型和转换过后的类型, 必须一致;  参数一: 指定某一列; 参数二: 用Map方法转换数据;
 //   sourceDF.na.replace("PM_Dongsi" , Map("NA" -> "NaN" , "NULL" -> "null")).show()
  }

}
