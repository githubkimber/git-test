package cn.itcast.spark.rdd

import org.apache.spark.util.AccumulatorV2
import org.apache.spark.{SparkConf, SparkContext}
import org.junit.Test

import scala.collection.mutable


class Accumulator {

@Test
def acc() : Unit = {

val conf = new SparkConf().setAppName("acc").setMaster("local[6]")
val sc = new SparkContext(conf)

// 创建累加器对象
val numAcc = new NumAccumulator()
// 注册给 Spark
sc.register(numAcc , "jdsh")
sc.parallelize(Seq("1" , "2" , "3")).foreach(x => numAcc.add(x))
println(numAcc.value)
sc.stop()


}
}

class NumAccumulator extends AccumulatorV2[String , Set[String]] {

private val nums : mutable.Set[String] = mutable.Set()

// 累加器对象是否是空的
override def isZero: Boolean = {
  nums.isEmpty
}

// 提供给 Spark 框架一个拷贝的累加器
override def copy(): AccumulatorV2[String, Set[String]] = {
  val newAccumulator = new NumAccumulator()
  nums.synchronized {
    newAccumulator.nums ++= this.nums
  }
  newAccumulator
}

// 清理累加器内容
override def reset(): Unit = {
  nums.clear()
}

// 在该方法中进行累加, 集合之间相加就用 ++= ; 集合加上变量,就用+= 即可;
override def add(v: String): Unit = {
  nums += v
}

/*
累加器在进行累加的时候, 可能每个分布式节点都用一个实例
在最后 Driver 进行一次合并, 把所有的实例的内容合并起来,调用这个merge方法进行合并
*/
override def merge(other: AccumulatorV2[String, Set[String]]): Unit = {
  nums ++= other.value
}

/*
提供给外部累加器结果
*/
override def value: Set[String] = {
  nums.toSet
}
}