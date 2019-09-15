package com.itheima.trans

import org.apache.flink.api.scala._

/*
张三,中国,江西省,南昌市
李四,中国,河北省,石家庄市
Tom,America,NewYork,Manhattan

转换为

(张三,中国)
(张三,中国,江西省)
(张三,中国,江西省,南昌市)
(李四,中国)
(李四,中国,河北省)
(李四,中国,河北省,石家庄市)
(Tom,America)
(Tom,America,NewYork)
(Tom,America,NewYork,Manhattan)
*/
object FlatmapTrans {

  def main(args: Array[String]): Unit = {

    // 创建执行环境
    val env = ExecutionEnvironment.getExecutionEnvironment

    //加载本地集合数据
    val listDataSet = env.fromCollection(
      List("张三,中国,江西省,南昌市" , "李四,中国,河北省,石家庄市"
       , "Tom,America,NewYork,Manhattan"))

    //flatMap
//    val flatmapData = listDataSet.flatMap(_.split(","))
//    flatmapData.print()

   val FlatMapData = listDataSet.flatMap{             //  这样怎么会都打印出来?
      text => val arr= text.split(",")
        List(
        (arr(0),arr(1)),
        (arr(0),arr(1),arr(2)),
        (arr(0),arr(1),arr(2),arr(3))
//        (arr(4),arr(5)),
//        (arr(4),arr(5),arr(6)),
//        (arr(4),arr(5),arr(6),arr(7)),
//        (arr(8),arr(9)),
//        (arr(8),arr(9),arr(10)),
//        (arr(8),arr(9),arr(10),arr(11))
        )
    }

    FlatMapData.print()
  }
}
