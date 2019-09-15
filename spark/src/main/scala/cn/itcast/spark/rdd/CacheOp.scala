package cn.itcast.spark.rdd

import org.apache.commons.lang3.StringUtils
import org.apache.spark.rdd.RDD
import org.apache.spark.storage.StorageLevel
import org.apache.spark.{SparkConf, SparkContext}
import org.junit.Test

class CacheOp {

  /*
1. 创建sc
2. 读取文件
3. 取出Ip, 赋予初始频率
4. 清洗
5. 统计Ip出现的次数
6. 统计出现次数最少的Ip
7. 统计出现次数最多的Ip
*/
@Test
def prepare(): Unit = {

// 1. 创建sc
val conf = new SparkConf().setAppName("cache_prepare").setMaster("local[6]")
val sc = new SparkContext(conf)
// 2. 读取文件
val source = sc.textFile("dataset/access_log_sample.txt")
// 3. 取出Ip, 赋予初始频率
val rdd1: RDD[String] = source.map(x => x.split(" - - ")(0))
// 4. 清洗
val rdd2 = rdd1.filter(x => StringUtils.isNotEmpty(x))
// 5. 统计Ip出现的次数
val rdd3 = rdd2.map(x => (x, 1))
val rdd4 = rdd3.reduceByKey((x, y) => x + y)
// 6. 统计出现次数最少的Ip
// val minIp = rdd4.map(x => x._2.min())
val rdd5 = rdd4.sortBy(_._2) // 先升序排序再取第一个
val lessIp = rdd5.first()
println(lessIp)
// 7. 统计出现次数最多的Ip
val rdd6 = rdd4.sortBy(_._2, false) // 降序排序再取第一个
val maxIp = rdd6.first()
println(maxIp)
}

@Test
def cache(): Unit = {

 // 1. 创建sc
 val conf = new SparkConf().setAppName("cache_prepare").setMaster("local[6]")
 val sc = new SparkContext(conf)
 // 2. 读取文件
 val source = sc.textFile("dataset/access_log_sample.txt")
 // 3. 取出Ip, 赋予初始频率
 val rdd1: RDD[String] = source.map(x => x.split(" - - ")(0))
 // 4. 清洗
 val rdd2 = rdd1.filter(x => StringUtils.isNotEmpty(x))
 // 5. 统计Ip出现的次数
 val rdd3 = rdd2.map(x => (x, 1))
 val rdd4 = rdd3.reduceByKey((x, y) => x + y).cache()   // 加入缓存

 // 6. 统计出现次数最少的Ip
 // val minIp = rdd4.map(x => x._2.min())
 val rdd5 = rdd4.sortBy(_._2) // 先升序排序再取第一个
 val lessIp = rdd5.first()
 println(lessIp)
 // 7. 统计出现次数最多的Ip
 val rdd6 = rdd4.sortBy(_._2, false) // 降序排序再取第一个
 val maxIp = rdd6.first()
 println(maxIp)
}

@Test
def persist(): Unit = {

// 1. 创建sc
val conf = new SparkConf().setAppName("cache_prepare").setMaster("local[6]")
val sc = new SparkContext(conf)
// 2. 读取文件
val source = sc.textFile("dataset/access_log_sample.txt")
// 3. 取出Ip, 赋予初始频率
val rdd1: RDD[String] = source.map(x => x.split(" - - ")(0))
// 4. 清洗
val rdd2 = rdd1.filter(x => StringUtils.isNotEmpty(x))
// 5. 统计Ip出现的次数
val rdd3 = rdd2.map(x => (x, 1))
val rdd4 = rdd3.reduceByKey((x, y) => x + y).persist(StorageLevel.MEMORY_ONLY)  // 设置缓存级别

// 6. 统计出现次数最少的Ip
// val minIp = rdd4.map(x => x._2.min())
val rdd5 = rdd4.sortBy(_._2) // 先升序排序再取第一个
val lessIp = rdd5.first()
println(lessIp)
// 7. 统计出现次数最多的Ip
val rdd6 = rdd4.sortBy(_._2, false) // 降序排序再取第一个
val maxIp = rdd6.first()
println(maxIp)
println(rdd4.getStorageLevel)
}

@Test
def checkpoint(): Unit = {

// 1. 创建sc
val conf = new SparkConf().setAppName("cache_prepare").setMaster("local[6]")
val sc = new SparkContext(conf)

// 设置保存checkpointDir的目录, 也可以设置为Hdfs上的目录.
sc.setCheckpointDir("checkpoint")

// 2. 读取文件
val source = sc.textFile("dataset/access_log_sample.txt")
// 3. 取出Ip, 赋予初始频率
val rdd1: RDD[String] = source.map(x => x.split(" - - ")(0))
// 4. 清洗
val rdd2 = rdd1.filter(x => StringUtils.isNotEmpty(x))
// 5. 统计Ip出现的次数
val rdd3 = rdd2.map(x => (x, 1))
val rdd4 = rdd3.reduceByKey((x, y) => x + y).persist(StorageLevel.MEMORY_ONLY)  // 设置缓存级别
// 斩断RDD依赖链 (这也相当于一个Action操作, 会重新计算一遍前面的算子,所以在此之前先缓存一下 )
rdd4.checkpoint()

// 6. 统计出现次数最少的Ip
// val minIp = rdd4.map(x => x._2.min())
val rdd5 = rdd4.sortBy(_._2) // 先升序排序再取第一个
val lessIp = rdd5.first()
println(lessIp)
// 7. 统计出现次数最多的Ip
val rdd6 = rdd4.sortBy(_._2, false) // 降序排序再取第一个
val maxIp = rdd6.first()
println(maxIp)
println(rdd4.getStorageLevel)
}

}