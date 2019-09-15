package com.itheima.sink

import org.apache.flink.api.scala._


/*
基于下列数据,分别 进行打印输出,error输出,collect()

    (19, "zhangsan", 178.8),
    (17, "lisi", 168.8),
    (18, "wangwu", 184.8),
    (21, "zhaoliu", 164.8)
*/
object BatchSinkCollection {

  def main(args: Array[String]): Unit = {

    // 创建执行环境
    val env = ExecutionEnvironment.getExecutionEnvironment

    // 获取数据
    val listDataSet = env.fromElements((19, "zhangsan", 178.8) , (17, "lisi", 168.8) , (18, "wangwu", 184.8) , (21, "zhaoliu", 164.8))

    // print 标准输出
    listDataSet.print()
    print(listDataSet)

    // printToErr 标准错误输出
    listDataSet.printToErr()

    // 本地Collection     collect() :该方法将数据转化到Buffer集合里
    val s = listDataSet.collect()
    print(s)
  }
}
