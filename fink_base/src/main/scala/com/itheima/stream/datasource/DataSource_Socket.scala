package com.itheima.stream.datasource


import org.apache.flink.streaming.api.scala._

object DataSource_Socket {

  def main(args: Array[String]): Unit = {

    // 创建流执行环境
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    // 构建socket数据源     socketTextStream :指定IP地址和端口号
    val socketDataStream = env.socketTextStream("node01" , 9999)

    // 数据转换, 空格切分
    val flatMapData = socketDataStream.flatMap(x => x.split(" "))

    // 打印
    flatMapData.print()

    // 执行任务
    env.execute()
  }
}
