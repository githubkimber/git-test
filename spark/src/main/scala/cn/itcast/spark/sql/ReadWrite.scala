package cn.itcast.spark.sql


import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}
import org.junit.Test

class ReadWrite {

  // 1.创建SparkSession
  val spark = SparkSession.builder()
    .appName("writer1")
    .master("local[6]")
    .getOrCreate()


  @Test
  def reader1() : Unit = {

    // 1.创建SparkSession
    val spark = SparkSession.builder()
      .appName("reader1")
      .master("local[6]")
      .getOrCreate()

    // 2.导入隐式转换的方法
    import spark.implicits._

    // 3.
    val sourceDF: DataFrame = spark.read.option("header" , true).csv("dataset/BeijingPM20100101_20151231_noheader.csv")
    sourceDF.show()
    println("==============================================================")
    sourceDF.show(10)
    println("==============================================================")
    sourceDF.printSchema()

  }

  @Test
  def reader2() : Unit = {

    // 1. 创建SparkSession
    val spark = SparkSession.builder()
      .appName("reader1")
      .master("local[6]")
      .getOrCreate()

    // 2. read  format 方法
    spark.read
      .format("csv")
      .option("header" , true)
      .option("inferSchema" , true)    // 推断Schema的数据类型
      .load("dataset/BeijingPM20100101_20151231_noheader.csv")
        .show(10)     // 查看10条

    println("==============================================================")

    // 2. csv文件格式方法属于上面方法的简写
    spark.read
      .option("header" , true)
      .option("inferSchema" , true)    // 推断Schema的数据类型
      .csv("dataset/BeijingPM20100101_20151231_noheader.csv")
      .show(10)     // 查看10条

  }

  @Test
  def writer1() : Unit = {

    // 1.创建SparkSession
    val spark = SparkSession.builder()
      .appName("writer1")
      .master("local[6]")
      .getOrCreate()

    // 2. 读取数据集
    val df: DataFrame = spark.read.option("header" , true).csv("dataset/BeijingPM20100101_20151231.csv")

    // 3. 写入数据集
    // 方式1.
    df.write.json("dataset/Beijing_PM01.json")

    // 方式2.
    df.write.format("json").save("dataset/Beijing_PM02.json")

  }

  @Test
  def parquet() : Unit = {
    // 1.读取csv 文件的数据
    val df = spark.read.option("header" , true).csv("dataset/BeijingPM20100101_20151231.csv")

    // 2.把数据写为Parquet格式
    df.write
      .format("Parquet")
        .mode("overwrite")
      .save("dataset/Beijing_PM03.Parquet")

    // 2.不指定格式,使用默认格式: (parquet)
    df.write
      .mode("overwrite")
      .save("dataset/Beijing_PM03")

    // 3.读取文件夹 来读取parqute格式文件
    spark.read
      .load("D:\\develop\\idea_workspace\\spark\\dataset\\Beijing_PM03")
      .show()
    spark.stop()
  }

  /*
   表分区的概念不仅在parquet上有, 其他格式的文件也可以指定表分区

  直接通过文件来进行读取, 分区星系会丢失
  spark SQL 会进行自动的分区发现
  */
  @Test
  def parquetpartitions() : Unit = {

//    // 1.读取文件
//    val df = spark.read
//      .option("header" , true)
//      .csv("dataset/BeijingPM20100101_20151231.csv")
//
//    // 2.写文件, 表分区
//    df.write
//      .mode("error")
//      .partitionBy("year" , "month")
//      .save("dataset/Beijing_pm4")

    // 3.读文件,自动发现分区
    spark.read
      .parquet("dataset/beijing_pm4")
      .printSchema()
  }

  @Test
  def json() : Unit = {

    // 生成文件
    val df = spark.read
      .option("header" , true)
      .csv("dataset/BeijingPM20100101_20151231.csv")

    // 写文件 json格式
    // 方法1.
    df.write
      .csv("dataset/Beijing_pm5")

    // 方法2.
    df.write
      .format("json")
      .mode("error")
      .save("dataset/Beijing_pm5")

  }

  /*
  toJson 的应用场景
      DataFrame中如果是一个对象, 如果其它的系统只支持Json格式的数据
      SparkSql 如果进行整合的话, 就需要进行转换
   */
  @Test
  def json1() : Unit = {

    // 生成文件
    val df = spark.read
      .option("header" , true)
      .csv("dataset/BeijingPM20100101_20151231.csv")

    // 写文件
    df.toJSON.show()

  }

  /*
  从消息队列中取出Json格式的数据, 需要使用SparkSql 进行处理
  */
  @Test
  def json2() : Unit = {

    // 生成文件
    val df = spark.read
      .option("header" , true)
      .csv("dataset/BeijingPM20100101_20151231.csv")


    val jsonRDD: RDD[String] = df.toJSON.rdd

    spark.read.json(jsonRDD).show()
  }
}
