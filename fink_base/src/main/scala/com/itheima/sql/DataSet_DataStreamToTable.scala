package com.itheima.sql

import org.apache.flink.core.fs.FileSystem
import org.apache.flink.streaming.api.scala._
import org.apache.flink.table.api.{Table, TableEnvironment}
import org.apache.flink.table.sinks.CsvTableSink


/*
以流处理方式，加载下列数据，并注册为表，查询所有数据，写入到CSV文件中。

  id  	product	  amount
  1   	beer   	  3
  2   	diaper 	  4
  3   	rubber 	  2
*/
case class Order1(id : Long , proudct : String , amount : Int)

object DataSet_DataStreamToTable {

  def main(args: Array[String]): Unit = {

    // 1. 获取流处理环境
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    // 2. 获取TableEnvironment
    val tableEnv = TableEnvironment.getTableEnvironment(env)

    // 3. 加载本地集合
    val listData = env.fromCollection(
      List(Order1(1 , "beer" , 3) ,
        Order1(2 , "diaper" , 4) ,
        Order1(3 , "rubber" , 2)
      ))

    // 4. 根据数据注册表
    tableEnv.registerDataStream("Order1" , listData)

    // 5. 执行SQL语句
    val table = tableEnv.sqlQuery("select * from Order1")

    // 6. 写入CSV文件中    参数一: 输出文件地址; 参数二: 字段之间分隔符; 参数三: 文件份的数量; 参数四: 写入模式;
    table.writeToSink(new CsvTableSink("./data/scorce_sql.scv" , "," , 1 ,FileSystem.WriteMode.OVERWRITE))

    // 打印表结构
    table.printSchema()

    // 7. 执行任务
    env.execute()

  }
}
