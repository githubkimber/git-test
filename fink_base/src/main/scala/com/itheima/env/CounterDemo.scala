package com.itheima.env

import org.apache.flink.api.common.accumulators.IntCounter
import org.apache.flink.api.common.functions.RichMapFunction
import org.apache.flink.api.scala._
import org.apache.flink.configuration.Configuration
import org.apache.flink.core.fs.FileSystem


/*
遍历下列数据, 打印出单词的总数

    "a","b","c","d"
*/

/*
1：创建累加器
private IntCounter numLines = new IntCounter();
2：注册累加器
getRuntimeContext().addAccumulator("num-lines", this.numLines);
3：使用累加器
this.numLines.add(1);
4：获取累加器的结果
myJobExecutionResult.getAccumulatorResult("num-lines")

 new RichMapFunction[in , out]{} :该方法, 参数一: 为输入类型, 参数二: 为输出类型, 后面跟实现该功能的方法体.
*/
object CounterDemo {

  def main(args: Array[String]): Unit = {

    // 创建执行环境
    val env = ExecutionEnvironment.getExecutionEnvironment

    // 获取数据
    val words = env.fromElements("a" , "b" , "c" , "d")

    // map转换
    val resultData = words.map(new RichMapFunction[String , String]{

      // 创建累加器
      val intCounter = new IntCounter

      override def open(parameters: Configuration): Unit = {
        // 注册累加器  参数一: 累加器名称; 参数二: 累加器对象.
        getRuntimeContext.addAccumulator("wordsCount" , intCounter)
      }

      override def map(in: String): String = {
        // 数据加载
        intCounter.add(1)
        in
      }
    })

    // 输出到文件
//    resultData.print()
    resultData.writeAsText("./data/counter" , FileSystem.WriteMode.OVERWRITE)

    // 执行任务   指定任务的名称: 自定义
    val jobExecutionResult = env.execute("counterDemo")

    // 获取累加器的数值   指定返回类型, 参数: 累加器名称
    val sumResult = jobExecutionResult.getAccumulatorResult[Int]("wordsCount")

    // 输出结果
    println(sumResult)

  }
}
