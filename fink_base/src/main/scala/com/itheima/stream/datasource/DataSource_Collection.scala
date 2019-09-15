package com.itheima.stream.datasource

//import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.scala._

object DataSource_Collection {

  def main(args: Array[String]): Unit = {

    // 创建执行环境
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    // 设置并行度个数
    env.setParallelism(4)

    // 指定数据源,加载本地集合
    val listDataStream = env.fromElements(1 , 2 , 3 , 4 , 5 , 6 , 7 , 8 , 9 , 0)

    // 打印数据
    listDataStream.print()

    // 执行任务     批处理环境下可以不执行, 但流处理环境下执行了才能输出到控制台
    env.execute()
  }
}
