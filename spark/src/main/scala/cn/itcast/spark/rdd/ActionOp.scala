package cn.itcast.spark.rdd

import org.apache.spark.{SparkConf, SparkContext}
import org.junit.Test

class ActionOp {

  val conf = new SparkConf().setMaster("local[6]").setAppName("Action_Op")
  val sc = new SparkContext(conf)

  /*
  需求: 最终生成("结果" , price)
   */
  @Test
  def reduce(): Unit = {

    val seq = Seq(("手机", 10.0), ("手机", 12.0), ("电脑", 20.0), ("相机", 18.0), ("手机", 10.0))
    val rdd1 = sc.parallelize(seq)
    // 该 reduce 中x与y都对应的是单个的元组  ; 如果用reduceByKey 则x与y都对应的是单个元组的velua值
    val rdd2 = rdd1.reduce((x, y) => ("总价: ", x._2 + y._2))

  }

  // 每次调用Action都会生成一个 job , job会运行获取结果, 所以在两个job中间有大量的Log打出,其实就是在启动日志
  // 数据倾斜 : 如果要解决数据倾斜的问题, 可以通过countByKey查看Key对应的数据量
  //
  @Test
  def count(): Unit = {

    val seq = Seq(("a", 1), ("jh", 5), ("hg", 5), ("j", 5))
    val rdd1 = sc.parallelize(seq)
    println(rdd1.count())
    println(rdd1.countByKey())

  }

  @Test
  def take(): Unit = {
    val seq = Seq(("a", 1), ("jh", 5), ("hg", 5), ("j", 5))
    val rdd1 = sc.parallelize(seq)
    println(rdd1.take(2).mkString)
  }

  @Test
  def first(): Unit = {
    val seq = Seq(("a", 1), ("jh", 5), ("hg", 5), ("j", 5))
    val rdd1 = sc.parallelize(seq)
    println(rdd1.first())
  }

  // takesample(withReplacement = true , 2)  :第一个参数为`withReplacement`, 意为是否取样以后是否还放回原数据集
  //  供下次使用 ; 第二个参数为随机抽取个数.
  @Test
  def Takesample(): Unit = {
    val seq = Seq(("a", 1), ("jh", 5), ("hg", 5), ("j", 5))
    val rdd1 = sc.parallelize(seq)
    val rdd2 = rdd1.takeSample(withReplacement = true, 2)
    println(rdd2.mkString)
  }

// max :最大值; min:最小值; sum :求和; mean :求和;
@Test
def number(): Unit = {
val seq = Seq(1, 2, 3, 4, 5, 6)
val rdd1 = sc.parallelize(seq)
println(rdd1.max())
println(rdd1.min())
println(rdd1.mean())
println(rdd1.sum())

}
}