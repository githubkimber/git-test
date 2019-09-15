package com.itheima.stream.window

import org.apache.flink.streaming.api.scala._

case class CountCart(sen : Int , cardNum : Int)

object CountTumblingWindow {

  def main(args: Array[String]): Unit = {

    // 创建流执行环境
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    // 配置socket
    val socketData = env.socketTextStream("node03", 10000)

    // 转换数据格式
    val mapData: DataStream[CountCart] = socketData.map {
    x => val s = x.split(",")
      CountCart(s(0).toInt , s(1).toInt)
  }

    // 执行统计, 每个sensorId一个tumbling窗口的大小为5秒, 按照key 进行收集,对应的key出现的次数达到5次作为一个结果
    val keyByData = mapData.keyBy(s => s.sen)
      .countWindow(5)     // countWindow(5)  :每5个消息一个窗口
      .sum(1)

    // 打印结果
    keyByData.print()

    // 执行任务
    env.execute()
  }
}
