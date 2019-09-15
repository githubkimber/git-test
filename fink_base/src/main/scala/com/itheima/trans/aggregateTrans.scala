package com.itheima.trans

import org.apache.flink.api.java.aggregation.Aggregations
import org.apache.flink.api.scala._


/*
请将以下元组数据，使用aggregate操作进行单词统计

    ("java" , 1) , ("java", 1) ,("scala" , 1)
*/
object aggregateTrans {

  def main(args: Array[String]): Unit = {

    // 创建执行环境
    val env = ExecutionEnvironment.getExecutionEnvironment

    // 获取本地数据
    val listDataSet = env.fromElements(("java" , 1) , ("java", 1) , ("scala" , 1))

    // 先分组 groupBY
    val groupByData = listDataSet.groupBy(0)      // 这里的 groupBy 的参数支持整型,不支持函数 ;

    // aggregate    使用 aggregate 对每个分组进行SUM , MAX , MIN 统计;
    val aggregateData = groupByData.aggregate(Aggregations.SUM , 1)

    // 打印
    aggregateData.print()

  }
}
