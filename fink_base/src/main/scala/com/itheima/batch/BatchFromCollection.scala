package com.itheima.batch

import org.apache.flink.api.scala._

object BatchFromCollection {

  def main(args: Array[String]): Unit = {

    // 1. 创建批处理运行环境
    val env : ExecutionEnvironment = ExecutionEnvironment.getExecutionEnvironment

    // 2.加载本地数据,String类型
    val localEle = env.fromElements("spark" , "flink")
    localEle.print()

    // 加载元祖类型
    val tupleDataset = env.fromElements(("spark" , 1) , ("flink" , 2))
    tupleDataset.print()

    // 加载本地集合
    val listDataset = env.fromCollection(List("flink" , "spark" , "hadoop"))
    listDataset.print()

    //  加载序列化数据
    val seqDataSet = env.generateSequence(1 , 9)
    seqDataSet.print()
  }
}
