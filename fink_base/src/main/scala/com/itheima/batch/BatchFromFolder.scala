package com.itheima.batch

import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.configuration.Configuration

object BatchFromFolder {

  def main(args: Array[String]): Unit = {

    // 创建执行环境
    val env = ExecutionEnvironment.getExecutionEnvironment

    // 读取目录
    def params = new Configuration()
    params.setBoolean("recursive.file.enumeration" , true)

    val folderDataSet = env.readTextFile("D:\\develop\\idea_workspace\\fink_base\\src\\main\\resources")
      .withParameters(params)

    folderDataSet.print()

  }

}
