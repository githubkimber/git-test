package com.itheima.sql

import org.apache.flink.api.scala._

import org.apache.flink.table.api.TableEnvironment


/*
使用Flink批处理环境, 加载下列集合数据, 转换为Table, 并将Table转换为DataSet

    List(
          (1L, 1, "Hello"),
          (2L, 2, "Hello"),
          (6L, 6, "Hello"),
          (7L, 7, "Hello World"),
          (8L, 8, "Hello World"),
          (20L, 20, "Hello World"))
        )
*/
object TableToDataSet {

  def main(args: Array[String]): Unit = {

    // 获取批处理环境
    val env = ExecutionEnvironment.getExecutionEnvironment

    // 设置并行度
    env.setParallelism(1)

    // 获取Table运行环境
    val tableEnv = TableEnvironment.getTableEnvironment(env)

    // 加载本地集合
    val listData = env.fromCollection(
      List((1L, 1, "Hello"),
        (2L, 2, "Hello"),
        (6L, 6, "Hello"),
        (7L, 7, "Hello World"),
        (8L, 8, "Hello World"),
        (20L, 20, "Hello World")
      ))

    // 转换DataSet为Table
    val table1 = tableEnv.fromDataSet(listData)

    // 将table装换为 DataSet ----将一个表附加到批上Append Mode
    val appendDataSet = tableEnv.toDataSet[(Long , Int , String)](table1)

    // 打印输出
    appendDataSet.print()

    // 执行任务
//    env.execute()

  }
}
