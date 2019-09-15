package com.itheima.env

import java.util.Date

import org.apache.flink.api.scala._

// 集合模式比本地模式运行速度快, 但当数据大于内存的时候就会报错, 适合小文件的时候运行

object BatchCollectionsEven {

  def main(args: Array[String]): Unit = {

    // 开始时间
    var start_time = new Date().getTime

    // 初始化本地执行环境
    val env = ExecutionEnvironment.createCollectionsEnvironment

    // 获取数据
    val list = env.fromCollection(List(1,2))

    // 打印
    list.print()

    // 结束时间
    var end_time = new Date().getTime

    // 输出时间差
    var timeData = end_time - start_time
    println(timeData+" 毫秒")

  }
}
