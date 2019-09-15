
import org.apache.commons.lang3.StringUtils
import org.apache.spark.metrics.source
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.junit.Test

class Test1 {

  @Test
def pmProcess() : Unit  = {

    // 1.创建sc对象
    val conf = new SparkConf().setMaster("local[6]").setAppName("stage_practice")
    val sc = new SparkContext(conf)
    // 2.读取文件
    val source = sc.textFile("dataset/BeijingPM20100101_20151231_noheader.csv")
    // 3.通过算子处理数据
    // 3.1 抽取数据 ,年 ,月 ,PM           rdd2 : ((年 , 月) , PM)
    val rdd2: RDD[((String, String), String)] = source.map(x => ((x.split(",")(1) , x.split(",")(2)) , x.split(",")(6)))
    //val rdd2 = source.map(x => x.split(","))
    //val rdd2 = rdd2.map(x => (((x(1) , x(2))) , x(6)))
    // 3.2 清洗                            filter :方法针对的是单个的元素,不是键值对, 所以 x._2 为上一个算子的 x.split(",")(6);
    val rdd3: RDD[((String, String), String)] = rdd2.filter(x => StringUtils.isNotEmpty(x._2) && ! x._2.equalsIgnoreCase("NA"))
    // 3.3 聚合
    val rdd4: RDD[((String, String), Int)] = rdd3.map(x => (x._1 , x._2.toInt)).reduceByKey((x, y) => x+y)

    // 3.4 排序
    val rdd5: RDD[((String, String), Int)] = rdd4.sortBy(x => x._2)
    // 4.获取结果
    rdd5.foreach(println(_))
    // 5.运行测试
    sc.stop()
  }

}
