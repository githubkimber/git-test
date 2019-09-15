package com.itheima.env

import org.apache.flink.api.common.functions.RichMapFunction
import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.configuration.Configuration


/*
遍历下列数据, 并在open方法中获取缓存的文件

    a,b,c,d

1：注册一个文件
env.registerCachedFile("hdfs:///path/to/your/file", "hdfsFile")
2：访问数据
File myFile = getRuntimeContext().getDistributedCache().getFile("hdfsFile");
*/
object BatchDemoDisCache {

  def main(args: Array[String]): Unit = {

    // 创建执行环境
    val env = ExecutionEnvironment.getExecutionEnvironment

    // 隐式转换
    import org.apache.flink.api.scala._

    // 注册文件       参数一: 文件路径,可以是HDFS的路径, 参数二: 文件路径的命名, 自定义
    env.registerCachedFile("D:\\develop\\idea_workspace\\fink_base\\src\\main\\resources\\data.txt" , "dataone.txt")

    // 读取数据
    val data1 = env.fromElements("a" , "b" , "c" , "d")

    val result = data1.map(new RichMapFunction[String , String] {
      // 获取输入
      override def open(parameters: Configuration): Unit = {

        // 获取文件
        val file = getRuntimeContext.getDistributedCache.getFile("dataone.txt")
      }

      // 转换输出
      override def map(in: String): String = {
        in
      }

    })

    result.print()
  }
}
