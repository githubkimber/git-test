package com.itheima.trans

import org.apache.flink.api.common.operators.Order
import org.apache.flink.api.scala._


/*
按照以下列表来创建数据集

    List("hadoop", "hadoop", "hadoop", "hive", "hive", "spark", "spark", "flink")

对分区进行排序后，输出到文件。
*/

case class s (filed : String)
object sortPartition {

  def main(args: Array[String]): Unit = {

    // 创建执行环境
    val env = ExecutionEnvironment.getExecutionEnvironment

    // 获取本地文件
    val listDataSet = env.fromCollection(List("hadoop", "hadoop", "hadoop", "hive", "hive", "spark", "spark", "flink"))

    // sortPartition 指定字段对分区中的数据进行排序   DESCENDING :降序排列
    val sortPartitionData = listDataSet.sortPartition(x => x.toString , Order.DESCENDING)

    // 打印
    sortPartitionData.print()

  }
}
