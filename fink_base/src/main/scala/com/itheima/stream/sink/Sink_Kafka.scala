package com.itheima.stream.sink

import com.itheima.stream.datasource.DataSource_MySql.Mysql_Source
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer010


object Sink_Kafka {

  def main(args: Array[String]): Unit = {

    // 创建流处理环境
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    // 设置并行度
    env.setParallelism(1)

    // 添加自定义mysql数据源
    val sqlData: DataStream[(Int, String, String, String)] = env.addSource(new Mysql_Source)

    // 转换元组数据为字符串
    val mapData = sqlData.map(x => x._1 + "" + x._2 + "" + x._3 + "" + x._4)

    // 构建 linkKafkaProducer010                                                         参数三: 序列化参数
    val kafkaCluster = "node01:9092,node02:9092,node03:9092"
    val flinkKafkaProducer = new FlinkKafkaProducer010[String](kafkaCluster, "test2", new SimpleStringSchema())

    // 添加sink
    mapData.addSink(flinkKafkaProducer)

    // 执行任务
    env.execute()
  }
}