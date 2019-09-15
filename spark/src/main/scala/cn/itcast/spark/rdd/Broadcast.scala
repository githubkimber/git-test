package cn.itcast.spark.rdd

import org.apache.spark.{SparkConf, SparkContext}
import org.junit.Test

class Broadcast {



val config = new SparkConf().setMaster("local[6]").setAppName("bc")
val sc = new SparkContext(config)
val seq = Seq("Spark" , "Scala")
val r = sc.parallelize(seq)

@Test
def bc1() : Unit = {
  val v = Map("Spark" -> "http://spark.apache.cn" , "Scala" -> "http://www.scala-lang.org")
// 将其中的Spark 和 Scala 转为对应的网址
val result = r.map(x => v(x)).collect()
println(result.mkString)
}

// 使用广播变量, 大幅减少value的复制
@Test
def bc2() : Unit = {
  val v = Map("Spark" -> "http://spark.apache.cn" , "Scala" -> "http://www.scala-lang.org")
// 创建广播
val bc = sc.broadcast(v)

// 在算子中使用广播变量代替直接引用集合,只会复制
val result = r.map(x => bc.value(x)).collect()
println(result.mkString)
}
}
