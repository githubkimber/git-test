package com.itheima.trans

import org.apache.flink.api.scala._
import org.apache.flink.core.fs.FileSystem


/*
基于以下列表数据来创建数据源，并按照hashPartition进行分区，然后输出到文件。

    List(1,1,1,1,1,1,1,2,2,2,2,2)
*/
object hashPartitionTrans {

  def main(args: Array[String]): Unit = {

    // 创建执行环境
    val env = ExecutionEnvironment.getExecutionEnvironment

    // 设置并行度                                    为什么文件夹里没有生成文件?
    env.setParallelism(2)

    // 获取本地文件
    val listDataSet = env.fromCollection(List(1,1,1,1,1,1,1,2,2,2,2,2))

    // hashPartition :按照指定的key进行hash分区        怎么用不了 hashPartition 这个方法?
//    listDataSet.hashPartition(x  => x.toString )

    val partitionData = listDataSet.partitionByHash(_.toString)

    // 打印
 //   partitionData.print()

    // 调用 writeAsText 写入文件到 data/partition_output 目录中                 为什么没有生成文件?
    partitionData.writeAsText("./data/partitions" , FileSystem.WriteMode.OVERWRITE )

    env.execute("klhuijh")

  }
}
