package com.itheima.env

import org.apache.flink.api.common.functions.RichMapFunction
import org.apache.flink.api.scala._
import org.apache.flink.configuration.Configuration


/*
创建一个学生数据集，包含以下数据

    |学生ID | 姓名 |
    |------|------|
    List((1, "张三"), (2, "李四"), (3, "王五"))

将该数据，发布到广播。

再创建一个成绩数据集，

    |学生ID | 学科 | 成绩 |
    |------|------|-----|
    List( (1, "语文", 50),(2, "数学", 70), (3, "英文", 86))

请通过广播获取到学生姓名，将数据转换为

    List( ("张三", "语文", 50),("李四", "数学", 70), ("王五", "英文", 86))
*/

/*
 new RichMapFunction[in , out]{} :该方法, 参数一: 为输入类型, 参数二: 为输出类型, 后面跟实现该功能的方法体.

 getRuntimeContext.getBroadcastVariable[(广播数据类型)](广播名)  获取广播变量

 withBroadcastSet  创建广播
*/
object BroadCast {

  def main(args: Array[String]): Unit = {

    // 创建执行环境
    val env = ExecutionEnvironment.getExecutionEnvironment

    // 获取数据
    val stuData = env.fromCollection(List((1, "张三"), (2, "李四"), (3, "王五")))
    val scoreData: DataSet[(Int, String, Int)] = env.fromCollection(List((1, "语文", 50),(2, "数学", 70), (3, "英文", 86)))

    // 使用 RichMapFunction 对成绩数据集进行map转换  :(学生ID , 学科 , 成绩) -> (学生姓名 , 学科 , 成绩)
    val resultData = scoreData.map(
      new RichMapFunction[(Int , String , Int) , (String , String , Int)]{
      var studentList : List[(Int , String)] = null

        // 重写open方法, 获取广播数据
        override def open(parameters: Configuration): Unit = {

          // 导入asScala.toList 的方法 将java List转换成scala List
          import scala.collection.JavaConverters._

          // 使用getRuntimeContext.getBroadcastVariable[广播数据类型](广播名)获取广播变量
          studentList = getRuntimeContext.getBroadcastVariable[(Int, String)]("student").asScala.toList
        }

        // 在map方法中进行广播转换
        override def map(in: (Int, String, Int)): (String, String, Int) = {
          // 获取学生Id
          val studentId = in._1

          // 过滤出学生Id的内容
          val tuples =  studentList.filter((x : (Int , String)) => x._1 == studentId)

          // 构建元组
          (tuples(0)._2 , in._2 , in._3)
        }
    }).withBroadcastSet(stuData , "student")      // 使用withBroadcastSet创建广播

    // 打印
    resultData.print()

  }
}
