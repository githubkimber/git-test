package com.itheima.stream.trans

import org.apache.flink.streaming.api.scala._



object Trans_KeyBy {

  def main(args: Array[String]): Unit = {

    // 1.获取流环境
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    // 设置并行度
    env.setParallelism(1)

    // 获取Socket连接
    val socketDataStream = env.socketTextStream("node01" , 9999)

    // 转换 以空格切分, 赋予词频 ,词频统计 (x , y) =>  x + y   这个sum求谁的和?  对keyBy 的2号元素求和
    val wordCount = socketDataStream.flatMap(_.split(" ")).map(( _ , 1 )).keyBy(_._1).sum(1)

    // 打印数据
    wordCount.print()

    // 执行任务
    env.execute()

  }

}
