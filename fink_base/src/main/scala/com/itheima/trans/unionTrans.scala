package com.itheima.trans

import org.apache.flink.api.scala._


/*
将以下数据进行取并集 , 合集 操作

数据集1

    "hadoop", "hive", "flume"

数据集2

    "hadoop", "hive", "spark"
*/
object unionTrans {

  def main(args: Array[String]): Unit = {

    // 创建执行环境
    val env = ExecutionEnvironment.getExecutionEnvironment

    // 获取本地文件
    val Data1 = env.fromElements("hadoop" , "hive" , "flume")
    val Data2 = env.fromElements("hadoop" , "hive" , "spark")

    // unoin  去两个DataSet的并集
    val unionData = Data1.union(Data2)

    // 打印
    unionData.print()
  }
}
