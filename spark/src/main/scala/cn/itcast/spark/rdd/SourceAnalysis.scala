package cn.itcast.spark.rdd

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.junit.Test

class SourceAnalysis {

@Test
def wordCount() : Unit = {

// 1. 创建sc对象
val conf = new SparkConf().setMaster("local[6]").setAppName("wordCount_source")
val sc = new SparkContext(conf)

// 2. 创建数据库
val seq = Seq("hadoop spark" , "hadoop flume" , "spark sqoop")
val source = sc.parallelize(seq)
// 3.数据处理
// 3.1 拆词
val rdd1 = source.flatMap(x => x.split(" , ")).flatMap(x => x.split(" "))
// 3.2 赋予初始词频
val rdd2 = rdd1.map(x => (x , 1))
// 3.3 聚合
val rdd3: RDD[(String, Int)] = rdd2.reduceByKey((x, y) => x + y)
// 3.4 将结果转换成字符窜  x  =>  s"${x}"
val rdd4: RDD[(String, String)] = rdd3.map(x => (x._1 , s"${x._2}"))
// 4.结果获取
// rdd4.collect().foreach(println)
  println(rdd4.toDebugString)
// 5.关闭sc ,
sc.stop()
}

// 需求: 求得两个Rdd之间的笛卡尔积(元素之间两两相交)
@Test
def narrowDependency() : Unit = {

// 1.生成Rdd
val conf = new SparkConf().setMaster("local[6]").setAppName("cartesian")
val sc = new SparkContext(conf)
val seq = Seq(1,2,3,4,5,6,7)
val seq1 = Seq("a" , "b" , "c")
val source1 = sc.parallelize(seq)
val source2 = sc.parallelize(seq1)
// 2.计算两个Rdd之间的笛卡尔积:   cartesian()
source1.cartesian(source2).collect().foreach(println)

}
}
