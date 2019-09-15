package com.itheima.trans

import org.apache.flink.api.scala._


/*
使用map操作，将以下数据

    "1,张三", "2,李四", "3,王五", "4,赵六"

转换为一个scala的样例类。
*/
case class Person(id : String , name : String)

object MapTrans {

  def main(args: Array[String]): Unit = {

    // 创建执行环境
    val env = ExecutionEnvironment.getExecutionEnvironment

    // 加载本地集合
    val listDataSet= env.fromCollection(List("1,张三" , "2,李四" , "3,王五" , "4,赵六"))

    // map
//    val mapData =  listDataSet.map(x => x.split(","))
//     mapData.print()

//    val flatMapData =  listDataSet.flatMap(x => x.split(","))
//     flatMapData.print()

    val personDataSet = listDataSet.map{
      text  => val arr = text.split(",")
      Person(arr(0) , arr(1))
    }

    // 打印
    personDataSet.print()
  }
}
