package com.itheima.trans

import org.apache.flink.api.scala._


/*
请将以下元组数据，使用distinct操作去除重复的单词

    ("java" , 1) , ("java", 2) ,("scala" , 1)

去重得到

    ("java", 1), ("scala", 1)
*/
object distinctTrans {

  def main(args: Array[String]): Unit = {

    // 创建执行环境
    val env = ExecutionEnvironment.getExecutionEnvironment

    // 获取本地数据
    val listDataSet = env.fromCollection(List(("java" , 1) , ("java", 2) ,("scala" , 1)))

    // distinc
    val distincData = listDataSet.distinct(x => x._1 )

    // 打印
    distincData.print()

  }
}
