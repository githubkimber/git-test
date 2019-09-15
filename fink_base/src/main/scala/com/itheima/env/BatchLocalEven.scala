package com.itheima.env

import java.util.Date

import org.apache.flink.api.scala._

// createLocalEnvironment()  :多线程 可设置并行度

object BatchLocalEven {

  def main(args: Array[String]): Unit = {

    // 开始时间
    var start_time = new Date().getTime

    // 创建本地执行环境
    val localEnv = ExecutionEnvironment.createLocalEnvironment(4)      // 括号里的参数为 :并行度个数

    //获取数据
    val listDataSet = localEnv.fromCollection(List(1,2,3,4))

    // 打印
    listDataSet.print()

    // 结束时间
    var end_time = new Date().getTime

    // 输出时间差
    var timeData = end_time - start_time
    println(timeData+" 毫秒")
  }
}
