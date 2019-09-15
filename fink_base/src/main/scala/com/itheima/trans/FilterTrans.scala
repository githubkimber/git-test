package com.itheima.trans

import org.apache.flink.api.scala._


/*
过滤出来以下以h开头的单词
"hadoop", "hive", "spark", "flink"
*/
object FilterTrans {

  def main(args: Array[String]): Unit = {

    // 1.创建批处理环境
    val env = ExecutionEnvironment.getExecutionEnvironment

    // 2.Source 加载本地集合
    val listDataSet = env.fromCollection(List("hadoop", "hive", "spark", "flink"))

    // filter       startWith("h")   该方法表示以h开头
    val filterData = listDataSet.filter(_.startsWith("h"))

    // 打印
    filterData.print()
  }
}