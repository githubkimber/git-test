package com.itheima.stream.trans


import org.apache.flink.streaming.api.scala._

object SplitAndSelect {

  def main(args: Array[String]): Unit = {

    // 创建流处理环境
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    // 设置并行度
    env.setParallelism(1)

    // 加载本地集合
    val listData = env.fromElements(1 , 2 , 3 , 4 , 5 , 6)

    // 数据分流, 分为奇数和偶数      match :匹配 ; even :双 ; odd :单 ;
    val splitData = listData.split{
      num =>
      num %2 match {
        case 0 => List("even")
        case 1 => List("odd")
      }
    }

    // 数据查询
 //   val all = splitData.select("even" , "odd")      // 这个也可以
    val even = splitData.select("even")

    // 打印数据
    even.print()

    // 执行任务
    env.execute()
  }
}
