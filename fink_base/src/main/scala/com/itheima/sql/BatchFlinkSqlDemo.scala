package com.itheima.sql



import org.apache.flink.api.scala._
import org.apache.flink.table.api. TableEnvironment


/*
使用Flink SQL统计用户消费订单的总金额、最大金额、最小金额、订单总数。

  订单id    	用户名     	订单日期            	消费金额
  1   	    zhangsan	  2018-10-20 15:30	  358.5

测试数据（订单ID、用户名、订单日期、订单金额）

(1,"zhangsan","2018-10-20 15:30",358.5),
(2,"zhangsan","2018-10-20 16:30",131.5),
(3,"lisi","2018-10-20 16:30",127.5),
(4,"lisi","2018-10-20 16:30",328.5),
(5,"lisi","2018-10-20 16:30",432.5),
(6,"zhaoliu","2018-10-20 22:30",451.0),
(7,"zhaoliu","2018-10-20 22:30",362.0),
(8,"zhaoliu","2018-10-20 22:30",364.0),
(9,"zhaoliu","2018-10-20 22:30",341.0)
*/
case class Order(id : Int , username : String , orderDate : String , money : Double)

object BatchFlinkSqlDemo {

  def main(args: Array[String]): Unit = {

    // 创建批处理环境
    val env = ExecutionEnvironment.getExecutionEnvironment

    // 获取Table运行环境
    val TableEnv = TableEnvironment.getTableEnvironment(env)

    // 获取本地集合
    val listData: DataSet[Order] = env.fromCollection(
      List(
        Order(1,"zhangsan","2018-10-20 15:30",358.5),
        Order(2,"zhangsan","2018-10-20 16:30",131.5),
        Order(3,"lisi","2018-10-20 16:30",127.5),
        Order(4,"lisi","2018-10-20 16:30",328.5),
        Order(5,"lisi","2018-10-20 16:30",432.5),
        Order(6,"zhaoliu","2018-10-20 22:30",451.0),
        Order(7,"zhaoliu","2018-10-20 22:30",362.0),
        Order(8,"zhaoliu","2018-10-20 22:30",364.0),
        Order(9,"zhaoliu","2018-10-20 22:30",341.0)
      ))

    // 使用Table运行环境将DataSet注册为一张表
    TableEnv.registerDataSet("table2" , listData)

    // 使用SQL语句来操作数据(统计用户消费订单的总金额, 最大金额, 最小金额, 订单总数)
    val sqlData= TableEnv.sqlQuery("select username ," +
      " sum(money) as totalmoney ," +
      " max(money) as maxMoney ," +
      " min(money) as minMoney ," +
      " count(1) as totalCount from table2 group by username")



    // 将table 转换为DataSet
    val DataSet = TableEnv.toDataSet[(String, Double, Double, Double, Long)](sqlData)

    // 打印输出
    DataSet.print
}

}
