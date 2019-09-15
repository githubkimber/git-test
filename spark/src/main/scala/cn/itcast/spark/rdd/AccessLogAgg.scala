package cn.itcast.spark.rdd

import org.apache.commons.lang3.StringUtils
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.junit.Test

class AccessLogAgg {

@Test
def IPAgg : Unit = {
// 1.创建SparkContext
val conf = new SparkConf().setMaster("local[6]").setAppName("IP_Agg")
val sc = new SparkContext(conf)
// 2.读取文件,生成数据集
val rdd1 = sc.textFile("dataset/access_log_sample.txt")   // 本地相对路径
// 3.取出IP,赋予次数为1
//val rdd2: RDD[Array[String]] = rdd1.map(x => x.split("\t"))
//val rdd3 = rdd2.map(x => x.split(" - - "))
val rdd2 = rdd1.map(x => (x.split(" ")(0) , 1))
//  val result1 = rdd2.collect()
//  println(result1.mkString)
// 4.简单清洗
// 4.1去掉空数据
// 4.2去掉非法数据
// 根据业务规整数据     StringUtils.isNotEmpty :字符串非空判断,不为空则true
val rdd3 = rdd2.filter(x => StringUtils.isNotEmpty(x._1))
// 5.根据IP出现的次数进行聚合
val rdd4 = rdd3.reduceByKey((_+_))
// 6.根据IP出现的次数进行排序 sortBy :排序(默认升序)  加上参数: ascending = false :降序排列
val rdd5 = rdd4.sortBy(_._2 , ascending = false)
// 7.取出并输出结果 前十个: take(x)
rdd5.take(10).foreach(x => println(x))
//val result = rdd5.collect()
//println(result.mkString)

}
}
