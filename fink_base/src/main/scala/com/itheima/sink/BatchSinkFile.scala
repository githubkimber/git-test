package com.itheima.sink

import org.apache.flink.api.scala._
import org.apache.flink.core.fs.FileSystem


/*
基于下列数据,写入到文件中

    Map(1 -> "spark", 2 -> "flink")
*/
object BatchSinkFile {

  def main(args: Array[String]): Unit = {

    // 创建执行环境
    val env = ExecutionEnvironment.getExecutionEnvironment

    // 获取数据     scala中的Map类型数据表示形式: Map(key -> value , key -> value ...)
    val listDataSet = env.fromCollection(Map(1 -> "spark", 2 -> "flink"))

    // writeAsText 写入文件
    listDataSet.setParallelism(2).writeAsText("./sink/test",FileSystem.WriteMode.OVERWRITE)
//    listDataSet.writeAsText("./sink/test")
    //listDataSet.setParallelism(1)           //  setParallelism() :设置并行度
     //.writeAsText("./sink/test" , FileSystem.WriteMode.OVERWRITE) // 文件写入模式: 覆盖

    // 打印                           // 打印的和视频里不一样?    fromCollection :为集合获取 , fromElement :为元素获取;
    listDataSet.print()

    //env.execute("sinkFile")     // 参数: 为执行任务的名称, 自定义

  }
}
