package cn.itcast.spark.rdd

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.junit.Test

class TransformationOp {
val conf = new SparkConf().setMaster("local[6]").setAppName("transformation_op")
val sc = new SparkContext(conf)

@Test
def mapPartitions() : Unit = {

// 1.数据生成   参数2 :分区数
val seq = Seq(1, 2, 3, 4, 5, 6, 2)
val rdd1 = sc.parallelize(seq)
// 2.算子使用
val rdd4 = rdd1.mapPartitions(x => {
  x.foreach(x => println(x));
  x // 函数返回替代值
}).collect() // 正确

val rdd2 = rdd1.mapPartitions(x => {
  x.foreach(x => (x * 10))
  x
}).collect()

val rdd3 = rdd1.mapPartitions(x => {
  x.map(x => x * 10)
}).collect()
  .foreach(x => println(x))
}

@Test
def mapPartitionsWithIndex() : Unit = {

  val seq = Seq(1,2,3,4,5,6)
  val rdd1 = sc.parallelize(seq , 2)

  val rdd2 = rdd1.mapPartitionsWithIndex((x , y) => {
    println("x: "+x)
    y.foreach(y => println("y: "+y))
    y
  }).collect()
}

@Test
def filter() : Unit = {

  // 1.定义集合
  val seq = Seq(1,2,3,4,5,6,7,8,9,10)
  val rdd1 = sc.parallelize(seq , 2)
  // 2.过滤数据
  val rdd2 = rdd1.filter(x => x%2 == 0).collect().foreach(x => println(x))
  // 3.收集数据
}
  // 3.获取结果

@Test
def sample() : Unit = {

  // 1.定义集合
  val seq = Seq(1, 2, 3, 4, 5, 6, 7, 8, 9, 0)
  val rdd1 = sc.parallelize(seq)
  // 2.过滤数据
  val rdd2 = rdd1.sample(true, 0.6)
  val result = rdd2.foreach(x => println(x))
  // 3.收集结果
}

  @Test
  def mapValues() : Unit = {

    // 1.定义集合
    val seq = Seq(("a" , 1) , ("b" , 2) , ("c" , 3) , ("d" , 4) , ("e" , 5))
    val rdd1 = sc.parallelize(seq)
    // 2.过滤数据
    val rdd2 = rdd1.mapValues((_*10))
    val result = rdd2.foreach(x => println(x))
    // 3.收集结果

}

// 交集
@Test
def intersection() : Unit = {

  val seq1 = Seq(1,2,3,4,5,6,7)
  val seq2 = Seq(5,6,7,8,9,0)
  val rdd1 = sc.parallelize(seq1)
  val rdd2 = sc.parallelize(seq2)
  rdd1.intersection(rdd2).foreach(x => println(x))
}

// 并集 (可能会有重复)
@Test
def union() : Unit = {
  val seq1 = Seq(1,2,3,4,5,6,7)
  val seq2 = Seq(5,6,7,8,9,0)
  val rdd1 = sc.parallelize(seq1)
  val rdd2 = sc.parallelize(seq2)
  rdd1.union(rdd2).foreach(println(_))
}

// 差集  (去除rdd1中与rdd2的相同的部分)
@Test
def subtract() : Unit = {
  val seq1 = Seq(1,2,3,4,5,6,7)
  val seq2 = Seq(5,6,7,8,9,0)
  val rdd1 = sc.parallelize(seq1)
  val rdd2 = sc.parallelize(seq2)
  rdd1.subtract(rdd2).foreach(println(_))
}

// 根据建分组
@Test
def groupByKey() : Unit = {

  val seq = Seq(("a", 1), ("b", 2), ("a", 3), ("b", 4), ("e", 5))
  val rdd1 = sc.parallelize(seq)
  val rdd2 = rdd1.groupByKey().collect().foreach(println(_))
}

// combineByKey() 对数据集按照 Key 进行聚合
// 转换数据的函数(初始函数, 作用于第一条数据, 用于开启整个计算), 在分区上进行聚合, 把所有分区的
// 聚合结果聚合为最终结果
@Test
def combineByKey() : Unit = {

  // 1.准备聚合
val seq = Seq(("张三" , 99.0) , ("张三" , 95.0) , ("李四" , 98.0) , ("李四" , 96.0) , ("张三" , 97.0))
val rdd1 = sc.parallelize(seq)
// 2.算子操作
// 2.1 createCombiner 转换数据
// 2.2 mergeValue 分区上的聚合
// 2.3 mergeCombiners 把所有分区上的结果再次聚合, 生成最终结果
// 3 获取并输出结果
val rdd2: RDD[(String, (Double, Int))] = rdd1.combineByKey(
  createCombiner = ( x : Double ) => (x , 1) ,
  mergeValue = (x :(Double , Int) , y : Double) => (x._1 + y , x._2 + 1) ,
  mergeCombiners = (x : (Double , Int) , y : (Double , Int)) => (x._1 + y._1 , x._2 + y._2)
)

// 输出查看combineByKey()结果
  rdd2.foreach(println(_))
// 计算平均分
val avge = rdd2.map( x =>x._2._1/x._2._2).collect().foreach(println(_))

}

// 和 ReduceByKey 是一样的, 都是按照 Key 做分组去求聚合, 但是 FoldByKey 的不同点在于可以指定初始值
@Test
def foldByKey() : Unit = {

val seq = Seq(("a", 1), ("b", 2), ("a", 3), ("b", 4), ("e", 5))
val rdd1 = sc.parallelize(seq)
val rdd2 = rdd1.foldByKey(10)((x , y) => x + y )
val rdd3 = rdd2.collect().foreach(println(_))
}

// aggregateByKey(zeroValue)(seqOp, combOp) 聚合所有 Key 相同的 Value
// 要求: 价格打八折再聚合
@Test
def aggregateByKey() : Unit = {

val seq = Seq(("手机" , 10.0) , ("手机" , 12.0) , ("电脑" , 20.0) , ("相机" , 18.0) , ("手机" , 10.0))
val rdd1 =sc.parallelize(seq)
val rdd2 = rdd1.aggregateByKey(0.8)((x , y) => y * x , (x , y) => x + y)
rdd2.collect().foreach(println(_))
}

// join() 将两个 RDD 按照相同的 Key 进行连接
@Test
def join() : Unit = {

  val seq1 = Seq(("a", 1), ("b", 2), ("a", 3), ("b", 4), ("e", 5))
  val seq2 = Seq(("a", 1), ("b", 2), ("c", 3), ("d", 4), ("e", 5))
  val rdd1 = sc.parallelize(seq1)
  val rdd2 = sc.parallelize(seq2)
  val rdd3 = rdd1.join(rdd2 ).collect().foreach(println(_))
}

// sortBy 排序(按实数大小排序) ; sortByKey 只有键值对类型的数据操作并且只对Key排序
@Test
def sort() : Unit = {

  val seq1 = Seq(("a", 1), ("b", 2.0), ("a", 33), ("b", 14), ("e", 5))
  val rdd3 = sc.parallelize(seq1)
  val seq = Seq(5,6,4,6,5.0,1,3,2,1,6)
  val rdd1 = sc.parallelize(seq)
  val rdd2 = rdd1.sortBy(x => x)
  rdd2.collect().foreach(println(_))

  val rdd4 = rdd3.sortByKey()
//  rdd4.collect().foreach(println(_))
}

//
@Test
def partitioning () : Unit = {

  val seq = Seq(1,2,3,4,5,6,7)
  val rdd1 = sc.parallelize(seq , 2)
  val rdd2 = rdd1.repartition(4).partitions.size
  val rdd3 = rdd1.coalesce(5 , shuffle = true).partitions.size
 // rdd3.foreach(println(_))
  println(rdd3)
}
}
