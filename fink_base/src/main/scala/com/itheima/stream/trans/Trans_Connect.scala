package com.itheima.stream.trans

import java.util.concurrent.TimeUnit

import org.apache.flink.streaming.api.functions.source.SourceFunction
import org.apache.flink.streaming.api.scala._

object Trans_Connect {

  def main(args: Array[String]): Unit = {

    // 创建流执行环境
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    // 配置数据源
    val longData = env.addSource(new MyLongSource)
    val StringData = env.addSource(new MyStringSource)

    // connect 连接
    val connectData = longData.connect(StringData)

    // 转换     map 还能这么写?  这个map是: ConnectedStreams下的方法
    val ds = connectData.map( x => x , y => y)

    // 打印数据
    ds.print().setParallelism(4)

    //执行任务
    env.execute("hgfg")

  }

}


// 实现一个从1开始递增的数字, 每个一秒生成一次
class MyLongSource extends SourceFunction[Long] {

  var isRunning = true
  var count = 0L

  override def cancel(): Unit = {
    // 运行到 cancel 时, 让它停止
    isRunning = false
  }

  override def run(ctx: SourceFunction.SourceContext[Long]): Unit = {

    while (isRunning) {
      count += 1
      ctx.collect(count)
      TimeUnit.SECONDS.sleep(1) // 睡眠一秒钟
    }
  }
}

// 实现一个从1开始递增的数字, 每个一秒生成一次
class MyStringSource extends SourceFunction[String]{

  var isRunning = true
  var count = 0L

  override def cancel(): Unit = {
    // 运行到 cancel 时, 让它停止
    isRunning = false
  }

  override def run(ctx: SourceFunction.SourceContext[String]): Unit = {

    while(isRunning){
      count += 1
      ctx.collect("Str_"+count)
      TimeUnit.SECONDS.sleep(1)  // 睡眠一秒钟
    }
  }
}