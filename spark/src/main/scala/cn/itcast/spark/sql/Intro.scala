package cn.itcast.spark.sql

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.catalyst.InternalRow
import org.apache.spark.sql.catalyst.encoders.RowEncoder
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}
import org.apache.spark.{SparkConf, SparkContext, sql}
import org.junit.Test

case class Person(name : String , age : Int)

class Intro {

@Test
def rddIntro(): Unit = {

  val conf = new SparkConf().setAppName("rdd intro").setMaster("local[6]")
  val sc = new SparkContext(conf)
  val source = sc.textFile("dataset/wordcount.txt")
  val rdd1 = source.flatMap(_.split("\r\n")).flatMap(_.split(" ")).map(x => (x, 1))
    .reduceByKey((x, y) => x + y)
  println(rdd1.collect().mkString)
}


@Test
def dsIntro(): Unit = {

  val spark = new sql.SparkSession.Builder() // Builder()  构建者模式
    .appName("ds intro")
    .master("local[6]")
    .getOrCreate() // 获取SparkSession对象

  // 导入隐式转换
  import spark.implicits._

  val sourceRDD: RDD[Person] = spark.sparkContext.parallelize(Seq(Person("zhangsan", 10), Person("lisi", 15)))

  val personDS = sourceRDD.toDS()

  val resultDS= personDS.where('age > 10)
    .where('age < 20)
    .select('name)
    .as[String]

  resultDS.show()
}

@Test
def dfIntro(): Unit = {

  val spark = new SparkSession.Builder()
    .appName("ds intro")
    .master("local[6]")
    .getOrCreate()

  import spark.implicits._

  val sourRDD: RDD[Person] = spark.sparkContext.parallelize(Seq(Person("张三", 10), Person("李四", 15)))

  val df: DataFrame = sourRDD.toDF()

  // dataFrame 注册
  df.createOrReplaceTempView("person")

  // SQL语句查询之前要注册
  val resultDf = spark.sql("select name from person where age > 10 and age < 20")

  resultDf.show()
}

@Test
def dataset1(): Unit = {

  // 1.创建SparkSession
  val spark = new sql.SparkSession.Builder()
    .appName("dataset1")
    .master("local[6]")
    .getOrCreate()
  // 2.导入隐式转换 // 这里的spark是上面new的对象
  import spark.implicits._
  // 3.演示
  val sourRDD = spark.sparkContext.parallelize(Seq(Person("张三", 10), Person("李四", 15)))

  val ds = sourRDD.toDS()

  // Dataset 支持强类型的API
  ds.filter(x => x.age > 10).show()

  // Dataset 支持弱类型的API
  ds.filter('age > 10).show()
  ds.filter($"age" > 10).show()

  // Dataset 可以直接编写SQL表达式
  ds.filter("age > 10").show()

}

// 底层类型
@Test
def dataset2(): Unit = {

  // 1. 创建SparkSession
  val spark: SparkSession = new sql.SparkSession.Builder()
    .master("local[6]")
    .appName("dataset2")
    .getOrCreate()

  // 2. 导入隐式转换
  import spark.implicits._

  // 3. 演示
  val sourceRDD = spark.sparkContext.parallelize(Seq(Person("张三", 10), (Person("李四", 15))))
  val dataset = sourceRDD.toDS()

  //  dataset.explain(true)
  val executionRdd: RDD[InternalRow] = dataset.queryExecution.toRdd
}

// 将dataset转换为同泛型的Rdd
@Test
def dataset3(): Unit = {
  // 1. 创建SparkSession
  val spark = new sql.SparkSession.Builder()
    .appName("dataset3")
    .master("local[6]")
    .getOrCreate()

  // 2. 导入隐式转换
  import spark.implicits._

  // 3. 演示
  val dataset: Dataset[Person] = spark.createDataset(Seq(Person("张三", 10), Person("李四", 15)))

  // .queryExecution.toRdd  :该API 直接获取已经分析和解析过的Dataset的执行计划,从中拿到Rdd
  val executionRdd: RDD[InternalRow] = dataset.queryExecution.toRdd

  // .rdd  :该API 通过将Dataset底层的RDD[internalRow] 通过Decoder 转成了和Dataset 一样的类型的Rdd
  val typedRdd: RDD[Person] = dataset.rdd

  println(executionRdd.toDebugString)
  println("====================================================================")
  println(typedRdd.toDebugString)

}

@Test
def dataframe1(): Unit = {

  // 1. 创建SparkSession
  val spark: SparkSession = new SparkSession.Builder()
    .master("local[6]")
    .appName("dataframe1")
    .getOrCreate()

  // 2. 导入隐式转换
  import spark.implicits._

  // 3.演示
  //val dataFrame = spark.sparkContext.parallelize(Seq(Person("张三" , 10) , Person("李四" , 15))).toDF()     这个也可以但麻烦
  val dataFrame = Seq(Person("张三", 10), Person("李四", 15)).toDF()

  dataFrame.where('age > 10)
    .select('name)
    .show()

}


@Test
def dataframe2(): Unit = {

  // 1. 创建SparkSession
  val spark = new SparkSession.Builder()
    .master("local[6]")
    .appName("dataframe1")
    .getOrCreate()

  // 2. 导入隐式转换
  import spark.implicits._

  // 3.演示
  val dataFrame1: DataFrame = spark.sparkContext.parallelize(Seq(Person("张三" , 10) , Person("李四" , 15))).toDF()   // 这个也可以但麻烦
  val dataFrame2: DataFrame = Seq(Person("张三", 10), Person("李四", 15)).toDF()

  val dataFrame3: DataFrame = spark.createDataFrame(Seq(Person("张三", 10), Person("李四", 15)))

  // csv :文件格式
  val dataFrame4: DataFrame = spark.read.csv("dataset/BeijingPM20100101_20151231_noheader.csv")

  dataFrame1.where('age > 10)
    .select('name)
    .show()

  println("================================")

  dataFrame2.where('age > 10)
    .select('name)
    .show()

  println("================================")

  dataFrame3.where('age > 10)
    .select('name)
    .show()

  dataFrame4.show()

}

@Test
def dataframe3() : Unit = {

  // 1.创建SparkSession
  val spark: SparkSession = SparkSession.builder()
    .appName("dataframe3")
    .master("local[6]")
    .getOrCreate()

  // 导入隐式转换
  import spark.implicits._

  // 演示
  val seq = Seq(Person("张三" , 10) , Person("李四" , 15))
  val sourceDF = spark.read.option("header" , value = true ).csv("dataset/BeijingPM20100101_20151231.csv")

  // 查看DataFrame的schema信息, DataFrame中有结构信息,叫做 Schema.

  sourceDF.printSchema()
  //sourceDF.show()

  // 1. 处理 : 选择列, 过滤NA的PM记录, 分组select year, month , PM_Dongsi , 聚合
  sourceDF.select('year , 'month , 'PM_Dongsi)
    .where('PM_Dongsi =!= "NA")
    .groupBy('year , 'month)
    .count()
    .show()

  // 可以直接使用sql语句查询
  // 先注册 任意取一个表名" pm "
  sourceDF.createOrReplaceTempView("pm")

  // 再可用SQL语句查询
  val resultDF = spark.sql("select year , month , count(PM_Dongsi) from pm where PM_Dongsi != 'NA' group by year , month")

  resultDF.show()

  spark.stop()

}

@Test
def dataframe4(): Unit = {

  // 1. 创建SparkSession
  val spark: SparkSession = new SparkSession.Builder()
    .master("local[6]")
    .appName("dataframe1")
    .getOrCreate()

  // 2. 导入隐式转换
  import spark.implicits._

  // 3.演示
  val dataFrame = Seq(Person("张三", 10), Person("李四", 15)).toDF()

  // DataFrame 是弱类型  x.get(0) :去下标为零的元素; x.getAs[Int](1) :件下标为1的元素转换为Int类型获取到;
  dataFrame.map(x => Row(x.get(0) , x.getAs[Int](1)))(RowEncoder.apply(dataFrame.schema))
    .show()

  // dataset 是强类型
  val dataset = Seq(Person("张三", 10), Person("李四", 15)).toDS()
  dataset.map(x => Person(x.name , x.age * 2))
    .show()

  // 弱类型编译期才会报错
//  dataFrame.groupBy("kldcmkl")
  // 强类型编辑时就会报错
//  dataset.map(x => x.jkshikj)
}
}
