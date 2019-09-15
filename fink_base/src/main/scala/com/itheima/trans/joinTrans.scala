package com.itheima.trans

import org.apache.flink.api.scala._


/*
在资料\测试数据源中，有两个csv文件，有一个为score.csv，一个为subject.csv，
分别保存了成绩数据以及学科数据。
(Int , String , Int , String)
需要将这两个数据连接到一起，然后打印出来。
*/

case class score(id : Int , name : String , StudentId : Int , score : Double )
case class subject(id : Int , name : String )
object joinTrans {

  def main(args: Array[String]): Unit = {

    // 创建执行环境
    val env = ExecutionEnvironment.getExecutionEnvironment

    // 获取本地文件     csv 文件读取必须使用元组或样例类;
    val sourceData = env.readCsvFile[score]("D:\\develop\\idea_workspace\\fink_base\\src\\main\\resources\\score.csv")
    val subjectData = env.readCsvFile[subject]("D:\\develop\\idea_workspace\\fink_base\\src\\main\\resources\\subject.csv")




    // 使用 join 连接两个DataSet , 并使用 where , equalto方法设置关联条件
    val joinData = sourceData.join(subjectData).where(2).equalTo(0)
//      .where(2)
//      .equalTo(2)

    joinData.print()
  }
}
