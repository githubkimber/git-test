package com.itheima.batch

import org.apache.flink.api.scala.ExecutionEnvironment

object BatchFromFile {

  def main(args: Array[String]): Unit = {

    // 创建env
    val env = ExecutionEnvironment.getExecutionEnvironment

    // 加载文件   readTextFile()
    val textDataSet = env.readTextFile("D:\\develop\\idea_workspace\\fink_base\\src\\main\\resources\\data.txt")

    // 打印
    textDataSet.print()
  }
}