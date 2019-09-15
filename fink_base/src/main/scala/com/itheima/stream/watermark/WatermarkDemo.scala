package com.itheima.stream.watermark

import java.util.UUID
import java.util.concurrent.TimeUnit

import org.apache.commons.lang.time.FastDateFormat
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks
import org.apache.flink.streaming.api.functions.source.{RichSourceFunction, SourceFunction}
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.watermark.Watermark
import org.apache.flink.streaming.api.windowing.time.Time

import scala.util.Random

object WatermarkDemo {

  // 创建一个订单样例类Order , 包含四个字段(订单ID , 用户Id , 订单金额 , 时间戳)
  case class Order(orderId : String , userId : Int , money : Long , timestamp : Long)

  def main(args: Array[String]): Unit = {

    // 创建流执行环境
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    // 设置处理时间为 EventTime
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)

    // 自定义数据源
    val orderData = env.addSource(new RichSourceFunction[Order] {

      var isRunning = true

      // 中断后的措施机制
      def cancel(): Unit = {
        isRunning = false
      }

      override def run(ctx: SourceFunction.SourceContext[Order]): Unit = {

        while (isRunning) {

          // 生成订单对象 :随机生成订单ID（UUID）, 用户ID（0-2）, 订单金额（0-100）, 时间戳为当前系统时间;
          val orderData = Order(UUID.randomUUID().toString, Random.nextInt(3), Random.nextInt(101), new java.util.Date().getTime)

          // 使用收集器收集
          ctx.collect(orderData)

          // 休息一秒钟 - 每隔1秒生成一个订单
          TimeUnit.SECONDS.sleep(1)
        }
      }
    })

    // 添加水印
    val watermarkData: DataStream[Order] = orderData.assignTimestampsAndWatermarks(new AssignerWithPeriodicWatermarks[Order] {

      // 允许延迟2秒
      val delayTime = 200L
      // 当前时间戳
      var currentTimestamp = 0L

      // 获取水印时间
      override def getCurrentWatermark: Watermark = {

        // 水印时间 等于 当前系统时间 减去 允许延迟时间
        var watermark = new Watermark(currentTimestamp - delayTime)

        // 时间格式转换
        val dataFarmart = FastDateFormat.getInstance("HH:mm:ss")

        // 水印时间 :类型及格式转换
        var waterDate = dataFarmart.format(watermark.getTimestamp)

        // 当前时间戳 :格式转换
        var currentTimeDate = dataFarmart.format(currentTimestamp)

        // 当前系统时间 :格式转换
        var systemDate = dataFarmart.format(System.currentTimeMillis())

        // 打印输出
        println(s"水印时间:${waterDate} , 事件时间:${currentTimeDate} , 系统时间:${systemDate} ")

        watermark
    }

      // 抽取当前时间戳
      override def extractTimestamp(element: Order, previousElementTimestamp: Long): Long = {

        // 比对两个元素的时间 ,求最大值
        currentTimestamp = Math.max(element.timestamp , previousElementTimestamp)
        currentTimestamp
      }
    })

    // 按照用户进行分流 , 设置5秒的时间窗口 , 统计
    val resultData = watermarkData.keyBy(x => x.userId)
      .timeWindow(Time.seconds(5))
      .reduce((x1 , x2) => Order(x1.orderId , x1.userId , x1.money + x2.money , 0L))

    // 打印输出
    resultData.print()

    // 执行任务
    env.execute()
  }
}
