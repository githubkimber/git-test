package com.itheima.stream.sink



import java.sql.{Connection, DriverManager, PreparedStatement}

import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction
import org.apache.flink.streaming.api.scala._

object Sink_MySql {

  def main(args: Array[String]): Unit = {

    // 创建流执行环境
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    // 获取数据
    val listData = env.fromCollection(List(
      (6 , "dazhuang", "123456", "大壮"),
        (7 , "erya", "123456", "二丫"),
          (8 , "sanpang", "123456", "三胖")
    ))

    // 设置数据源
    listData.addSink(new MySqlSink)

    //执行任务
    env.execute()

  }

}

class MySqlSink extends RichSinkFunction[(Int , String , String , String)]{

  var connection : Connection = null
  var ps: PreparedStatement = null

  override def open(parameters: Configuration): Unit = {

    // 加载MySql驱动
    Class.forName("com.mysql.jdbc.Driver")

    // 创建数据库连接"jdbc:mysql://localhost:3306/test" , "root" , "123"
    connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "123")

    // 创建PreparedStatement
    val sql = "insert into user(id , username , password , name) values(? , ? , ? , ?)"
    ps = connection.prepareStatement(sql)
  }
    override def invoke(value: (Int , String , String , String)) ={

      // 执行插入
      ps.setInt(1 , value._1)
      ps.setString(2 , value._2)
      ps.setString(3 , value._3)
      ps.setString(4 , value._4)

      // 执行更新
      ps.executeUpdate()
    }

  override def close(): Unit = {

    // 关闭连接
    if(connection != null){
      connection.close()
    }

    if(ps != null){
      ps.close()
    }

  }
}