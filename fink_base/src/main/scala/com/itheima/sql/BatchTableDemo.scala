package com.itheima.sql

import org.apache.flink.api.scala._
import org.apache.flink.core.fs.FileSystem
import org.apache.flink.table.api.{Table, TableEnvironment, Types}
import org.apache.flink.table.sinks.CsvTableSink
import org.apache.flink.table.sources.CsvTableSource

object BatchTableDemo {

  def main(args: Array[String]): Unit = {

    // 创建批处理环境
    val env = ExecutionEnvironment.getExecutionEnvironment

    // 创建table运行环境
    val tableEnv = TableEnvironment.getTableEnvironment(env)

    // 加载外部csv文件
    val tableData = CsvTableSource.builder()
      .path("./data/scorce_sql.scv")                  // 加载文件路径
      .field("id" , Types.INT)              // 列名 , 类型定义
      .field("name" , Types.STRING)
      .field("classId" , Types.STRING)
      .field("number" , Types.INT)
      .fieldDelimiter(",")          // 字段间分隔符
      .lineDelimiter("\n")          // 换行符
      .ignoreFirstLine()            // 是否忽略第一行, 默认情况不忽略,
      .ignoreParseErrors()          // 有异常就抛出,先走完程序.
      .build()

    // 将外部数据数据构建成表
    tableEnv.registerTableSource("table1" , tableData)

    // 使用table方式查询数据
    val table2 = tableEnv.scan("table1")
      .select("id , name , classId , number")
      .filter("name == '张三'")

    // 打印表结构
    table2.printSchema()

    // 将数据落地到新的CSV文件中
    table2.writeToSink(new CsvTableSink("./data/scorce_table.scv" , "," , 1 , FileSystem.WriteMode.OVERWRITE))

    // 执行任务
    env.execute()
  }
}
