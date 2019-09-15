package com.itheima.stream.datasource

import java.util.concurrent.TimeUnit
import java.util.{Date, UUID}
import org.apache.flink.streaming.api.functions.source.{RichSourceFunction, SourceFunction}
import org.apache.flink.streaming.api.scala._

import scala.util.Random

/*
new RichSourceFunction[]{}  :中括号的参数是返回值类型
*/

// 创建订单样例类
case class Order(id : String , userId : String , money : Int , time : Long)

object DataSource_Custom {

  def main(args: Array[String]): Unit = {

    // 创建流处理环境
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    // 创建自定义数据源
    val resultData = env.addSource(new RichSourceFunction[Order] {

    override def run(sourceContext: SourceFunction.SourceContext[Order]): Unit = {

      // 题目要求循环1000次
      for (i <- 0 until 1000) {

        // 随机生成订单ID
        val id = UUID.randomUUID().toString

        // 随机生成用户Id在(0~2)之间的整数
        val userId = Random.nextInt(3).toString()

        // 随机生成订单金额在(0~100)之间的整数
        val money = Random.nextInt(101)

        // 时间戳为当前系统时间
        val time1 = new Date().getTime
        val time = System.currentTimeMillis()

        // 上下文收集数据
        sourceContext.collect(Order(id, userId, money, time))

        // 每隔一秒执行一次循环; 相当于每执行一次休息一秒
        TimeUnit.SECONDS.sleep(1)
      }
      }

      override def cancel(): Unit = {}
    })

    // 打印数据
    resultData.print()

    // 执行数据
    env.execute()
  }
}
