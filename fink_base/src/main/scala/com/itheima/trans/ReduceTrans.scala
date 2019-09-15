package com.itheima.trans

import org.apache.flink.api.scala._


/*
请将以下元组数据，使用reduce操作聚合成一个最终结果

    ("java" , 1) , ("java", 1) ,("java" , 1)

将上传元素数据转换为("java",3)
*/
object ReduceTrans {

  def main(args: Array[String]): Unit = {

    // 创建执行环境
    val env = ExecutionEnvironment.getExecutionEnvironment

    // 读取本地数据
//    val listDataSet = env.fromElements(("java" , 1) , ("java" , 1) , ("java" , 1))            这个也可以
    val listDataSet = env.fromCollection(List(("java" , 1) , ("java" , 1) , ("scala" , 1)))

    // reduce     // 这里的参数x1 , x2 ,指的是数据源的元素1, 元素2, 向后递归
    val reduceData = listDataSet.reduce((x1, x2) => (x1._1 , x1._2 + x2._2))

    //打印
    reduceData.print()

  }
}
