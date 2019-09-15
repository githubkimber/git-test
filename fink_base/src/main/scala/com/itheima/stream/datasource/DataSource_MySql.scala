package com.itheima.stream.datasource

import java.sql.DriverManager

import org.apache.flink.streaming.api.functions.source.{RichSourceFunction, SourceFunction}
import org.apache.flink.streaming.api.scala._

object DataSource_MySql {

  // 自定义Source , 继承自RichSourceFunction
  class Mysql_Source extends RichSourceFunction[(Int , String , String , String)]{

    override def cancel(): Unit = {}

    // 实现run方法
    override def run(ctx: SourceFunction.SourceContext[(Int, String, String, String)]): Unit = {

      // 加载驱动
      Class.forName("com.mysql.jdbc.Driver")

      // 创建连接
      val connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test" , "root" , "123")

      // 创建sql语言对象  PreparedStatement()
      val sql = "select id , username , password , name from user"
      val ps = connection.prepareStatement(sql)

      // 执行查询   executeQuery()
      val resultData = ps.executeQuery()

      // 便利查询结果,收集数据
      while(resultData.next()){
        val id = resultData.getInt("id")
        val username = resultData.getString("username")
        val password = resultData.getString("password")
        val name = resultData.getString("name")

        // 收集数据
        ctx.collect((id , username , password , name))
      }
    }
  }

  def main(args: Array[String]): Unit = {

    // 创建流式执行环境
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    // 使用自定义Source
//    val mySqlDataStream = env.addSource(new RichSourceFunction[Mysql_Source] {override def cancel(): Unit = {}
//
//      override def run(ctx: SourceFunction.SourceContext[Mysql_Source]): Unit = {}      为什么不能用这种方法, 直接把SQL连接写在run方法里.
//    })
    val mySqlDataStream = env.addSource(new Mysql_Source)

    // 打印结果
    mySqlDataStream.print()

    // 执行任务
    env.execute("jhgh")
  }
}
