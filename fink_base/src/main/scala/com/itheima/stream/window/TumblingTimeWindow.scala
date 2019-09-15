package com.itheima.stream.window



import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time

object TumblingTimeWindow {

  /*
  参数一: 多少个红绿灯 ; 参数二: 多少辆车
  */
  case class CountCar (sen : Int , carNum : Int)

  def main(args: Array[String]): Unit = {

    // 创建流执行环境
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    // 定义数据源 socket nc -lk 9999
    val socketData: DataStream[String] = env.socketTextStream("node03" , 9999)

    // 转换数据
    val mapData = socketData.map{
      x =>  val s = x.split(",")
        CountCar(s(0).toInt , s(1).toInt)
    }

    // 执行统计 以绿灯进行分组
    val sumData = mapData.keyBy(_.sen)
      .timeWindow(Time.seconds(5))
      .sum(1)

    // 打印结果
    sumData.print()

    // 执行任务
    env.execute()
  }
}
