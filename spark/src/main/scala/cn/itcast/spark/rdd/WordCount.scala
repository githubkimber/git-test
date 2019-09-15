package cn.itcast.spark.rdd

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.junit.Test

class WordCount {
  def main(args: Array[String]): Unit = {

    // 1.创建SparkCountext
    val conf = new SparkConf().setMaster("local[6]").setAppName("word_count")
    val sc = new SparkContext(conf)

    // 2.加载文件
    // 2.1准备文件
    // 2.2读取文件
    val rdd1: RDD[String] = sc.textFile("hdfs://node01/data/wordcount.txt")

    // 3.处理:
    // 3.1把整句话拆分成单个单词
    val rdd2: RDD[String] = rdd1.flatMap(x => x.split(" "))
    // 3.2把每个单词制定一个词频为1
    val rdd3: RDD[(String, Int)] = rdd2.map((_ , 1))
    // 3.2根据建聚合
    val rdd4 = rdd3.reduceByKey(_+_)

    // 得到结果
    val result = rdd4.collect()
//    println(result)
    result.foreach(x => println(x))
  }

@Test
def sparkContext() : Unit = {
  // 1. 创建 SparkConf
  val conf = new SparkConf().setMaster("local[6]").setAppName("apark_context")
  // 2. 创建 SparkContext
  val sc = new SparkContext(conf)
}

  // 1. 创建 SparkConf
  val conf: SparkConf = new SparkConf().setMaster("local[6]").setAppName("apark_context")
  // 2. 创建 SparkContext
  val sc: SparkContext = new SparkContext(conf)


// 从本地集合创建
@Test
def rddCreationlocal() : Unit = {

val seq = Seq("Hello1" , "Hello2" , "Hello3")
// 得到的变量rdd1类型与参数1: seq类型相同; 参数2为: 分区数
// 方式1>.  parallelize(seq , 2)
val rdd1 : RDD[String] = sc.parallelize(seq , 2)
// 方式2>.  makeRDD(seq , 2)
val rdd2 : RDD[String] = sc.makeRDD(seq , 2)
}

// 从文件创建
@Test
def rddCreationFile() : Unit = {

  sc.textFile("file:///...")
  // 传入路径也可以是 : "hdfs://node01:8020/...."  ;
  // 分区是由hdfs中文件的block决定的
}

// 从Rdd衍生
@Test
def rddCreateFromRdd() : Unit = {

val rdd1: RDD[Int] = sc.parallelize(Seq(1 , 2 , 3))
// 在rdd上执行算子操作,会生成新的rdd
val rdd2 : RDD[Int] = rdd1.map(x => x)
}

@Test
def mapTest() : Unit = {

// 1.创建Rdd
val rdd1 = sc.parallelize(Seq(1 , 2 , 3))
// 2.执行map操作
val rdd2 = rdd1.map(x => x * 10)
// 3.得出结果
val result = rdd2.collect()
  println(result.mkString)
  result.foreach(x => println(x))
}

@Test
def flatMapTest() : Unit = {

// 1. 创建 Rdd
val seq = Seq("hello lily" , "hello lucy" , "bye lily")
val rdd1 = sc.parallelize(seq)
//// 2.flatMap处理数据
val rdd2 = rdd1.flatMap(x => x.split(" "))

// 3.得出结果
val result = rdd2.collect()
  println(result.mkString)
  result.foreach(x => println(x))
// 4.关闭result
  sc.stop()
}

@Test
def reduceBykeyTest() : Unit = {

// 1.创建rdd
val seq = Seq("hello lily" , "hello lucy" , "bye lily")
val rdd1 = sc.parallelize(seq)
// 2.flatMap处理数据
val rdd2 = rdd1.flatMap(x => x.split(" "))
// 3.给词频赋值
val rdd3 = rdd2.map(( _ , 1))
// 4.reduceByKey处理数据
val rdd4 = rdd3.reduceByKey((x , y) => x + y)
// 简化后: val rdd5 = rdd3.reduceByKey(_+_)
// 5.得出结果
val result = rdd4.collect()
  result.foreach(x => println(x))
}
}
