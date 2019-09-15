package com.itheima

import java.util.concurrent.TimeUnit
import org.apache.flink.api.java.tuple.Tuple
import org.apache.flink.runtime.state.filesystem.FsStateBackend
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.checkpoint.ListCheckpointed
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks
import org.apache.flink.streaming.api.functions.source.{RichSourceFunction, SourceFunction}
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.scala.function.WindowFunction
import org.apache.flink.streaming.api.watermark.Watermark
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector


// 开发自定义数据源

// 1. 自定义样例类(id: Long, name: String, info: String, count: Int)
case class Msg (id: Long, name: String, info: String, count: Int)

// 1. 自定义数据源,继承RichSourceFunction
class MySource extends RichSourceFunction[Msg]{

  var isRunning = true

  override def cancel(): Unit = {
      isRunning = false
  }
  // 1. 实现run方法, 每秒钟向流中注入10000个样例类
  override def run(ctx: SourceFunction.SourceContext[Msg]): Unit = {

    while(isRunning){
      for (i <- 0 until 10000){
       // 收集数据
       ctx.collect(Msg(0L , "name_"+i , "test_info" , 0))       // 这里面的数据凭什么能任一写?
      }
      // 休眠1s
      TimeUnit.SECONDS.sleep(1)
    }
  }
}


// 开发自定义状态

// 1. 继承Serializable
class UDFState extends Serializable{

  private var count = 0L

  // 1. 为总数count提供set和get方法
  def setState(s:Long) = count = s
  def getState = count
}


// 开发自定义Window和检查点

// 1. 继承WindowFunction
class myWindowAndCheckpoint extends WindowFunction[Msg , Long , Tuple , TimeWindow] with ListCheckpointed[UDFState]{

  // 求总数和
  var total = 0L

  // 1. 重写apply方法,对窗口数据进行总数累加
  override def apply(key: Tuple, window: TimeWindow, input: Iterable[Msg], out: Collector[Long]): Unit = {

    var count = 0L

    for (msg <- input){
      count = count + 1
    }
    total = total + count

    // 计数统计收集
    out.collect(total)
  }

  // 3. 重写restoreState,恢复自定义快照
  override def restoreState(state: java.util.List[UDFState]): Unit = {

    // 从List集合中取值
    val udfState = state.get(0)

    // 取出检查点的值, 赋值给total
    total = udfState.getState
  }

  // 2. 重写snapshotState,制作自定义快照
  override def snapshotState(checkpointId: Long, timestamp: Long): java.util.List[UDFState] = {

    // 创建UDFState对象
    var udfState = new UDFState

    // 创建List集合
    val udfList = new java.util.ArrayList[UDFState]()

    udfState.setState(total)

    //将UDFState对象添加到集合
    udfList.add(udfState)

    // 返回数据
    udfList
  }
}





// 开发主业务
object CheckpointDemo {

  def main(args: Array[String]): Unit = {

    // 1. 流处理环境
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    // 2. 开启checkpoint,间隔时间为6s
    env.enableCheckpointing(6000)

    // 3. 设置checkpoint位置    也可以是 "hdfs://node01:8020/..."
    env.setStateBackend(new FsStateBackend("file///D:\\text\\dev_checkpoint"))

    // 4. 设置处理时间为事件时间
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)

    // 5. 添加数据源
    val sourceData: DataStream[Msg] = env.addSource(new MySource)

    // 6. 添加水印支持      assignTimestampsAndWatermarks() :该方法为数据流中的元素分配时间戳，并定期创建水印以指示事件时间进度。
    // AssignerWithPeriodicWatermarks  : 该类为元素分配事件时间时间戳
    val watermarkData: DataStream[Msg] = sourceData.assignTimestampsAndWatermarks(new AssignerWithPeriodicWatermarks[Msg] {

      // getCurrentWatermark  :返回当前水印。系统周期性地调用该方法以检索当前水印。该方法可以返回{@code null}以指示没有新的Watermark可用。
      override def getCurrentWatermark: Watermark = {

        // Watermark()  :使用给定的时间戳创建一个新的水印，以毫秒为单位。
        new Watermark(System.currentTimeMillis())
      }

      /* extractTimestamp()  :为元素分配时间戳，自Epoch以来以毫秒为单位。
       该方法传递先前分配的元素时间戳。可以通过摄取时间从先前的分配器分配先前的时间戳。
       如果元素之前没有时间戳，则该值为{@code Long.MIN_VALUE}。
       */
      override def extractTimestamp(element: Msg, previousElementTimestamp: Long): Long = {

        // 按系统时间生成时间戳
        System.currentTimeMillis()
      }
    })

    // 7. keyby分组
    val keyedStream: KeyedStream[Msg, Tuple] = watermarkData.keyBy(0)     // 这个数据类型怎么生成的?

    // 设置窗口时间, 窗口时间为4s
    val windowStream: WindowedStream[Msg, Tuple, TimeWindow] = keyedStream.timeWindow(Time.seconds(4) , Time.seconds(1))

    // 9. 指定自定义窗口
    val result = windowStream.apply(new myWindowAndCheckpoint)

    // 10. 打印结果
    result.print()

    // 11. 执行任务
    env.execute()



  }
}
