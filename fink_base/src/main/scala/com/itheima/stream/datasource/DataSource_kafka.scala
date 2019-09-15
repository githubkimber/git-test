package com.itheima.stream.datasource

import java.util.Properties

import org.apache.flink.api.common.serialization.SimpleStringSchema

import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010
import org.apache.kafka.clients.CommonClientConfigs

object DataSource_kafka {

  def main(args: Array[String]): Unit = {

    // 创建流式执行环境
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    // 指定kafka相关信息                                                   我的 Kafka 启动不起来
    val kafkaCluster = "node01:9092 , node02:9092 , node03:9092"
    val kafkaTopic = "kafkatopic"       // kafka话题名称 ; 在linux窗口 启动时自定义

    // 创建kafka数据流                               new SimpleStringSchema() :反序列化器
    val props = new Properties()
    props.setProperty(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG , kafkaCluster)    // Common Client Config 通用客户端配置 ;   bootstrap  servers config 引导服务器配置

    val flinkKafkaConsumer = new FlinkKafkaConsumer010[String](kafkaTopic , new SimpleStringSchema() , props)

    // 设置数据源
    val KafkaDataStream = env.addSource(flinkKafkaConsumer)


    // 打印数据
    KafkaDataStream.print()

    // 执行任务
    env.execute()
  }
}
