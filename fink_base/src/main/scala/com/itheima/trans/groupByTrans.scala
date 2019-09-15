package com.itheima.trans

import org.apache.flink.api.scala._


object groupByTrans {

  def main(args: Array[String]): Unit = {

    // 创建执行环境
    val env = ExecutionEnvironment.getExecutionEnvironment

    // 获取本地数据
    val listDataSet = env.fromCollection(List(("java" , 1) , ("java" , 1) , ("scala" , 1)))

    // groupBy      分组
    val groupByData = listDataSet.groupBy(x => x._1)

    // reduce       聚合
    val reduceData = groupByData.reduce((x1 , x2) => (x1._1 , (x1._2 + x2._2)))
    reduceData.print()

  }

}
