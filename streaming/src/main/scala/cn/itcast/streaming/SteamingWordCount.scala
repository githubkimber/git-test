package cn.itcast.streaming

import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf}

/*
在SparkCore 中的内存, 创建SparkContext 的时候使用
在创建Streaming Context 的时候也要用到 conf , 说明Spark Streaming 是基于 Spark Core 的
在执行master 时候, 不能指定一个线程
因为在 Streaming 运行的时候, 需要一个新的线程来一直监听数据的获取

StreamingContext 其实就是Spark Streaming 的入口
相当于SparkContext 是 Spark Core 的入口一样
它们叫做 ...Context
*/
object SteamingWordCount {

  def main(args: Array[String]): Unit = {

    // 1.初始化环境
    val conf = new SparkConf()
      .setAppName("Streaming")
        .setMaster("local[2]")        // 线程数必须大于1

    // 2.参数一: 源数据; 参数二:  处理一批数据的时间
    val ssc = new StreamingContext(conf , Seconds(5) )
    ssc.sparkContext.setLogLevel("WARN")      // 设置日志级别


    // socketTextStream 这个方法用于创建一个DStream, 监听Socket 输入, 当做文本来处理
    // sparkContext.textFile() 创建一个rdd , 他们两类似, 都是创建对应的数据集
    // Rdd -> Spark Core DStream -> Spark Streaming
    // DStreaming 可以理解为是一个流式的RDD
    val lines = ssc.socketTextStream(
      hostname = "192.168.110.141" ,  // ip
      port = 9999 ,                    // 端口
      storageLevel = StorageLevel.MEMORY_AND_DISK_SER    // 存储级别: 记忆和磁盘设置;
    )

    // 2.数据的处理
    // 2.1.把句子拆为单词
    val words = lines.flatMap(_.split(" "))

    // 2.2.赋予词频
    val tuples = words.map(x => (x, 1))

    // 2.3.词频统计reduce
    val counts= tuples.reduceByKey((x, y) => (x + y))

    // 3.展示和启动
    counts.print()

    ssc.start()
    // main 方法执行完毕后就会退出, 所以需要阻塞主线程
    ssc.awaitTermination()

  }

}
