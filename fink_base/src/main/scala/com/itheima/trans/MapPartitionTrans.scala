package com.itheima.trans

import org.apache.flink.api.scala._


/*
使用mapPartition操作，将以下数据

    "1,张三", "2,李四", "3,王五", "4,赵六"

转换为一个scala的样例类。
*/
object MapPartitionTrans {

/*
mapPartition与map的区别:
map: 一次只传递一个字符串,
mapPartition: 一次传递多个字符串,把一个分区的数据都传递过来,拿到这些数据后自行的进行 map 转换或其他转换
 */

  def main(args: Array[String]): Unit = {

    // 创建执行环境
    val env = ExecutionEnvironment.getExecutionEnvironment

    // 读取本地数据
    val listDataSet = env.fromCollection(List("1,张三" , "2,李四" , "3,王五" , "4,赵六"))

    // mapPartition
    val mapDataSet = listDataSet.mapPartition {
      iterable => {                              // iterable :代表分区元素集合
      iterable.map {
        text =>
          val arr = text.split(",")
          Person(arr(0), arr(1))
      }
    }
    }
    mapDataSet.print()
  }
}
