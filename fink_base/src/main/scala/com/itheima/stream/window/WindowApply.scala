package com.itheima.stream.window




import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.scala.function.WindowFunction
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector




object WindowApply {

  def main(args: Array[String]): Unit = {

    // 获取流执行环境
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    // 配置socket数据源
    val socketData = env.socketTextStream("node03" , 10000)

    // 转换数据   map(x -> 1) :词频统计
    val mapData = socketData.flatMap(x => x.split(",")).map((_ , 1))

    // 统计 : 使用`timeWinodw`指定窗口的长度（每3秒计算一次）
    val timeWindowData: DataStream[(String, Int)] = mapData.keyBy(_._1)
      .timeWindow(Time.seconds(3))

      // 实现一个WindowFunction 匿名内部类
      .apply(new WindowFunction[(String , Int) , (String , Int) , String , TimeWindow] {

      // 在apply方法中实现聚合计算
        override def apply(key: String, window: TimeWindow, input: Iterable[(String, Int)], out: Collector[(String, Int)]): Unit = {

          val wordCount = input.reduce((x1, x2) => (x1._1 , x1._2 + x2._2))


          // 使用Collector.collect收集数据
          out.collect(wordCount)
      }
    })
          // 打印输出
          timeWindowData.print()

          // 执行任务
          env.execute()
  }
}
