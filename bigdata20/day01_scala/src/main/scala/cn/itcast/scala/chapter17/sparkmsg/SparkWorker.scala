package cn.itcast.scala.chapter17.sparkmsg

import java.util.UUID

import scala.concurrent.duration._
import akka.actor.{Actor, ActorRef, ActorSelection, ActorSystem, Props}
import cn.itcast.scala.chapter11.HeartBeat
import cn.itcast.scala.chapter17.sparkmsg.util.{RegisterMessage, RegisteredMessage, SendHeartBeat}
import com.typesafe.config.{Config, ConfigFactory}

class SparkWorker extends Actor{
println("构造器调用")

private val workId = UUID.randomUUID().toString
private val memory : Int = 128
private val cores : Int = 16

var selection : ActorSelection = _

  override def preStart(): Unit = {
println("SparkWorker前置执行方法")
// worker向master发送一个注册信息
selection = context.actorSelection("akka.tcp://masterActorSystem@192.168.132.1:8888/user/sparkMaster")
// 发送注册信息
selection ! RegisterMessage(workId , memory , cores)
}

override def receive: Receive = {
case "connect" => println("connected")
case RegisteredMessage(msg) => {
  println(msg)
  // 调用定时模块,定时的向master发送心跳的信息
  // 没法直接向sparkMaster发送数据, 只能向自己发数据,因为自己本身就是一个ActorRef
  import context.dispatcher
  context.system.scheduler.schedule(0 millis , 10000 millis , self , HeartBeat)
}
case HeartBeat => {
  // 接受到了自己的心跳信息, 再发给master心跳信息
  println("准备发送心跳信息")
  selection ! SendHeartBeat(workId)
}
}
}

object SparkWorker{
  def main(args: Array[String]): Unit = {


    val host = "192.168.132.1"
    val port = 6666
    val configStr : String =
      s"""
         |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
         |akka.remote.netty.tcp.hostname = "$host"
         |akka.remote.netty.tcp.port = "$port"
       """.stripMargin

    val config : Config = ConfigFactory.parseString(configStr)
    // 获取actorSystem
    val workerActorSystem = ActorSystem("workerActorSystem" , config)
    val sparkWorker : ActorRef = workerActorSystem.actorOf(Props(new SparkWorker) , "SparkWorker")

  }
}