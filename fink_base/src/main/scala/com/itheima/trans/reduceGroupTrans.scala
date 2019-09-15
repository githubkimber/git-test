package com.itheima.trans

import org.apache.flink.api.scala._


/*
请将以下元组数据，下按照单词使用groupBy进行分组，再使用reduceGroup操作进行单词计数

    ("java" , 1) , ("java", 1) ,("scala" , 1)
*/
object reduceGroupTrans {

  def main(args: Array[String]): Unit = {

    // 创建执行环境
    val env = ExecutionEnvironment.getExecutionEnvironment

    // 获取本地数据
    val listDataSet = env.fromElements(("java" , 1) , ("java" , 1) , ("scala" , 1))

    // 需要groupBy
    val groupByData = listDataSet.groupBy(x => x._1)

    // reduceGroup
    val reduceGroupData = groupByData.reduceGroup(
      iter =>
      iter.reduce((x1 , x2 ) => (x1._1 , x1._2 + x2._2))
    )

    // 打印
    reduceGroupData.print()

  }

}
