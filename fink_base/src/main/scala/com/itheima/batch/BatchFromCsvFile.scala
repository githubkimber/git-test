package com.itheima.batch

import org.apache.flink.api.scala._

case class Subject(id:Long , name:String)

object BatchFromCsvFile {

  def main(args: Array[String]): Unit = {

    // 创建执行环境
    val env = ExecutionEnvironment.getExecutionEnvironment

    // 加载csv 文件, csv是以 , 分割文本内容           问题:怎么用元组做?
    case class Subject(id:Long , name:String)
    val caseClassDataSet= env.readCsvFile[Subject]("D:\\develop\\idea_workspace\\fink_base\\src\\main\\resources\\subject.csv")

    caseClassDataSet.print()

  }
}
