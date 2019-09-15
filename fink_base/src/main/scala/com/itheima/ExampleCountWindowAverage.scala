package com.itheima

import org.apache.flink.api.common.functions.RichFlatMapFunction
import org.apache.flink.api.common.state.{ValueState, ValueStateDescriptor}
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.api.scala._
import org.apache.flink.util.Collector

//                               RichFlatMapFunction<IN, OUT>
class CountWindowAverage extends RichFlatMapFunction[(Long, Long), (Long, Long)] {

  // 声明状态值
  private var sum: ValueState[(Long, Long)] = _

  // 重写flatMap方法
  override def flatMap(input: (Long, Long), out: Collector[(Long, Long)]): Unit = {

    // 通过value()方法获取状态的当前值
    val tmpCurrentSum = sum.value

    // 如果状态值为空,赋予(0L , 0L) ,否则返回默认值
    val currentSum = if (tmpCurrentSum != null) {
      tmpCurrentSum
    } else {
      (0L, 0L)
    }

    // 累加数据 , 更新计数
    val newSum = (currentSum._1 + 1, currentSum._2 + input._2)

    // 更新状态
    sum.update(newSum)

    // 当数据累加到大于等于2,就会去求平均值, 接着清理状态值
    if (newSum._1 >= 2) {
      out.collect((input._1, newSum._2 / newSum._1))
      sum.clear()
    }
  }

  override def open(parameters: Configuration): Unit = {
    sum = getRuntimeContext.getState(       // 参数一: 自定义名称;  参数二: 指定类型
      new ValueStateDescriptor[(Long, Long)]("average", createTypeInformation[(Long, Long)])
    )
  }
}


object ExampleCountWindowAverage extends App {

  // 创建流执行环境
  val env = StreamExecutionEnvironment.getExecutionEnvironment

  // 获取数据
  env.fromCollection(List(
    (1L, 3L),
    (1L, 5L),
    (1L, 7L),
    (1L, 4L),
    (1L, 2L)
  )).keyBy(_._1)                          // 根据元组的第一个元素分组
    .flatMap(new CountWindowAverage())    // 自定义 flatMap 方法;
    .print()
  // the printed output will be (1,4) and (1,5)

  env.execute("ExampleManagedState")
}