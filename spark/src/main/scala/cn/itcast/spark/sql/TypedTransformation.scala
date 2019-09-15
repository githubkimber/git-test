package cn.itcast.spark.sql


import java.lang


import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}
import org.apache.spark.sql.types._
import org.junit.Test
case class Student(name : String , age : Int , gpa : Float)

/*
数据源后面,如果没有 toDS() 转换成dataset类型就没法使用其算子;
*/
class TypedTransformation {

  // 1.创建SparkSession
  val spark = SparkSession.builder()
    .appName("hjgbh")
    .master("local[6]")
    .getOrCreate()

  // 2.导入隐式转换
  import spark.implicits._

  @Test
  def trans(): Unit = {

    // 方式一. 获取数据
    val ds1 = Seq("hello spark", "hello hadoop").toDS()
    ds1.flatMap(_.split(" ")).show()

    // 方式一. 获取数据
    val ds2 = Seq(Person("张三", 10), Person("李四", 15)).toDS()
    ds2.map(x => Person(x.name, x.age * 2)).show()

    // mapPartitions
    ds2.mapPartitions(
      // 不能大到内存放不下,
      // 对每个元素转换, 后生成一个集合
      x => {
        val result = x.map(x => Person(x.name, x.age * 2))
        result
      }
    )
  }

  /*
  range(10) :该方法 生成[0~10) 10个数字;
  transfrom :对数据集的转换;   withColum 创建新的列
  */
  @Test
  def trans1(): Unit = {

    val ds = spark.range(10)
    ds.transform(x => x.withColumn("doubled", 'id * 2))
      .show()
  }

  @Test
  def as(): Unit = {
    // 1.读取
    val schema = StructType {
      Seq(
        StructField("name", StringType),
        StructField("age", IntegerType),
        StructField("gpa", FloatType)
      )
    }

    val df: DataFrame = spark.read
      .schema(schema)
      .option("delimiter", "\t")
      .csv("dataset/studenttab10k")

    // 2.转换
    val ds: Dataset[Student] = df.as[Student]

    // 3.输出
    ds.show()
  }

  @Test
  def filer(): Unit = {
    import spark.implicits._

    val ds = Seq(Person("张三", 10), Person("李四", 15)).toDS()

    ds.filter(x => x.age > 10).show()
  }

  @Test
  def groupByKey(): Unit = {

    val ds = Seq(Person("张三", 10), Person("张三", 15), Person("李四", 20)).toDS()

    // select count(*) from person group by name;
    val rdd1 = ds.groupByKey(x => x.name)
    val result = rdd1.count()

    result.show()

  }

  @Test
  def split() : Unit = {
    val ds = spark.range(15)

    // randomSplit , 切多少份, 权重多少 (按权重比例大致分隔)
    val rdd1: Array[Dataset[lang.Long]] = ds.randomSplit(Array(1,2,3,4))

    // 打印
    rdd1.foreach(x => x.show())

    // sample 采样  参数一: 是否放回重复采样; 参数二: 采样百分比;

    ds.sample(withReplacement = false , fraction = 0.5)
      .show()

  }

  @Test
  def sort () : Unit = {
    val ds = Seq(Person("张三" , 10) , Person("李四" , 15)).toDS()
    ds.orderBy('age.desc).show()    // orderBy 是 sort(分类) 的别名 'age 列名前加单引号代表这一列,
    ds.sort('age.asc).show()        // desc 表示降序排列, asc 表示升序排列

  }

// distinct 去重复 (元素之间内容完全重复才去除)
// dropDupliucates() 指定元素中某一共同特点去重复
  @Test
  def dropDuplicatasets() : Unit = {
    val ds = spark.createDataset(Seq(Person("张三" , 10) , Person("张三" , 10) , Person("李四" , 10)))

    ds.distinct()
      .show

    ds.dropDuplicates("age")
      .show()
  }

  @Test
  def collection() : Unit = {
    val ds1 = spark.range(1 , 10)
    val ds2 = spark.range(5 , 15)

    // 差集
    ds1.except(ds2)
      .show()

    // 交集
    ds1.intersect(ds2)
      .show()

    // 并集(合集允许有重复)
    ds1.union(ds2)
      .show()

    // 输出限制
    ds1.limit(5)
      .show()
  }

}
