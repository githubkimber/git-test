package com.itheima.stream.datasource

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment

/*
readTextFile  :txt , csv , 本地文本 , Hdfs文件远程文件都可以读
*/

object DataSource_CSV {

  def main(args: Array[String]): Unit = {

    // 流处理环境
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    // 读取HDFS文件
    val hdfsData = env.readTextFile("hdfs://node01:8020/test/input/README.txt")

    // 打印
    hdfsData.print()

    // 执行任务
    env.execute()
  }
}
