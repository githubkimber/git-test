package com.itheima.sql

import java.util.UUID
import java.util.concurrent.TimeUnit
import org.apache.commons.lang.time.FastDateFormat
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks
import org.apache.flink.streaming.api.functions.source.{RichSourceFunction, SourceFunction}
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.watermark.Watermark
import org.apache.flink.table.api.{Table, TableEnvironment}
import org.apache.flink.types.Row

import scala.util.Random

case class Order2 (Id : String , userId : Int , money : Double , time01 : Long)

class OrderSource extends RichSourceFunction[Order2] {

  private var isRunning = true

  override def cancel(): Unit = {
    isRunning = false
  }


  override def run(ctx: SourceFunction.SourceContext[Order2]): Unit = {
    while (isRunning) {
      for (i <- 0 until 100) {
        // 随机生成订单Id
        val Id = UUID.randomUUID().toString

        // 随机生成用户Id[0~2]
        val userId: Int = Random.nextInt(3)

        // 随机生成订单金额[0~100]
        val money: Double = Random.nextInt(101).toDouble

        // 时间戳为当前系统时间
        val time01 = System.currentTimeMillis()

        // 收集数据
        ctx.collect(Order2(Id , userId , money , time01))

        TimeUnit.SECONDS.sleep(1)
      }
    }
  }
}

object StreamFlinkSqlDemo {

  def main(args: Array[String]): Unit = {

    // 创建流处理环境
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    // 创建table运行环境
    val tableEnv = TableEnvironment.getTableEnvironment(env)

    // 设置处理时间为"Eventime"
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)

    // 创建一个数据源
    val sourceData = env.addSource(new OrderSource)

    // 添加水印, 允许延迟2秒
    val watermarkData = sourceData.assignTimestampsAndWatermarks(
      new AssignerWithPeriodicWatermarks[Order2] {

        var currentTimestamp : Long =_

        // 允许延迟两秒
        var delayTime = 2000

      // 生成一个水印数据
      override def getCurrentWatermark: Watermark = {
        val watermark = new Watermark(currentTimestamp - delayTime)
        val formater = FastDateFormat.getInstance("HH:mm:ss")

//        println(s"水印时间: ${formater.format(watermark.getTimestamp)} ," +
//          s" 事件时间: ${formater.format(currentTimestamp)} , 系统时间: ${formater.format(System.currentTimeMillis())}")
        watermark
      }

      // 从Order中获取对应的时间戳
      override def extractTimestamp(element: Order2, previousElementTimestamp: Long): Long = {

        // 获取到Order订单事件的时间戳
        val time01 = element.time01

        // 表时间轴不会往前推, 不能因为某些数据延迟, 导致整个window数据得不到计算
        currentTimestamp = Math.max(currentTimestamp , time01)
        currentTimestamp
      }
    })

    // 导入table隐式转换
    import org.apache.flink.table.api.scala._

    // 注册表, 并分别制定字段, 还要指定rowtime字段
    tableEnv.registerDataStream("table1" , watermarkData , 'Id , 'userId , 'money ,'time1.rowtime)

    // 编写SQL :统计用户订单总数 ,最大金额 ,最小金额 ,分组时要使用'tumble(时间列)
    val sql =
      """
        |select userId , count(1) as totaluserId,
        |max(money) as maxmoney,
        |min(money) as minmoney from table1
        |group by tumble(time1 , interval '5' second),
        |userId
      """.stripMargin

    // 使用tableEnv.sqlQuery执行sql语句
    val sqlquery = tableEnv.sqlQuery(sql)

    // 将table转换为DataStream
    val dataStream= tableEnv.toAppendStream[Row](sqlquery)

    // 打印输出
    dataStream.print()

    env.execute()

  }
}