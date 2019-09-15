package com.itheima.sql

import org.apache.flink.streaming.api.scala._
import org.apache.flink.table.api.TableEnvironment

/*
List(
      (1L, 1, "Hello"),
      (2L, 2, "Hello"),
      (6L, 6, "Hello"),
      (7L, 7, "Hello World"),
      (8L, 8, "Hello World"),
      (20L, 20, "Hello World"))
    )
*/

object TableToDataStream {

  def main(args: Array[String]): Unit = {

    // 创建流处理环境
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    // 设置并行度
    env.setParallelism(4)

    // 获取table运行环境
    val tableEnv = TableEnvironment.getTableEnvironment(env)

    // 加载本地集合
    val listdata = env.fromCollection(
      List((1L, 1, "Hello"),
      (2L, 2, "Hello"),
      (6L, 6, "Hello"),
      (7L, 7, "Hello World"),
      (8L, 8, "Hello World"),
      (20L, 20, "Hello World")))

    // 转换listData为Table :
    val table1 = tableEnv.fromDataStream(listdata)

    // 将table 转换为DataStream  :将一个表附加到流上Append Mode
    val appendDataStream = tableEnv.toAppendStream[(Long , Int , String)](table1)

    // 将table转换为DataStream  :Retract Mode true 代表添加消息, false代表撤销消息
    val retractDataStream = tableEnv.toRetractStream[(Long , Int , String)](table1)

    // 打印输出
    appendDataStream.print()

    // 执行任务
    env.execute()

  }
}
