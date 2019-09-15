package com.itheima.trans

import org.apache.flink.api.common.functions.RichMapFunction
import org.apache.flink.api.scala._


/*
 Flink也会产生数据倾斜的时候，例如：当前的数据量有10亿条，在处理过程就有可能发生如下状况：
 rebalance会使用轮询的方式将数据均匀打散，这是处理数据倾斜最好的选择。
*/
object rebalanceTrans {

  def main(args: Array[String]): Unit = {

    // 创建执行环境
    val env = ExecutionEnvironment.getExecutionEnvironment

    // 获取本地数据     使用 env.generateSequence 创建 0~100 的并行数据
    val seqDataSet = env.generateSequence(0 , 100)

    // 题目要求: 使用 fiter 过滤出来 大于8 的数字
    val filterData = seqDataSet.filter(x => x>8 ).rebalance()

    // 使用map操作传入RichMapFunction，将当前子任务的ID和数字构建成一个元组
    // 在RichMapFunction中可以使用`getRuntimeContext.getIndexOfThisSubtask` :获取子任务序号
    val resultData = filterData.map(new RichMapFunction[Long , (Int , Long)] {

      // value :每次遍历的数字
      override def map(value: Long): (Int, Long) = {
        (getRuntimeContext.getIndexOfThisSubtask , value)
      }
    })

    // 打印
    resultData.print()

  }
}
