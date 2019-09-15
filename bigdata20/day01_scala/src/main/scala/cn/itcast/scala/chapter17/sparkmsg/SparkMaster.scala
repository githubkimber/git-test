package cn.itcast.scala.chapter17.sparkmsg

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import cn.itcast.scala.chapter17.sparkmsg.util.{RegisterMessage, RegisteredMessage, SendHeartBeat, WorkerInfo}
import com.typesafe.config.{Config, ConfigFactory}

import scala.collection.mutable

class SparkMaster extends Actor{
  println("master构造器")
  override def preStart(): Unit = {
    println("前置方法")
  }


private val workMsgMap = new mutable.HashMap[String , WorkerInfo]()

  override def receive: Receive = {
    case "connect" =>println("connected")
      // 得到worker注册信息
    case RegisterMessage(workId , memory , cores) => {
    val workInfo = new WorkerInfo(workId , memory , cores)
    workMsgMap.put(workId , workInfo)
    sender ! RegisteredMessage("恭喜你注册成功!!!")
    }
    case SendHeartBeat(workId) => {
      // 获取SparkWorker的心跳信息
      println("接受心跳的workId为"+workId)
      // 判断保存的注册信息是否在map当中存在
      if(!workMsgMap.contains(workId)){
        // 通过workId获取workInfo
        val workInfo : WorkerInfo = workMsgMap(workId)
        // 更新心跳时间
        workInfo.lastHeartHeatTime = System.currentTimeMillis()
      }
    }
  }
}

object SparkMaster{
  def main(args: Array[String]): Unit = {

    val host = "192.168.132.1"
    val port = 8888
    val configStr : String =
      s"""
         |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
         |akka.remote.netty.tcp.hostname = "$host"
         |akka.remote.netty.tcp.port = "$port"
       """.stripMargin

    val config : Config = ConfigFactory.parseString(configStr)
    // 获取actorSystem
    val masteractorSystem = ActorSystem("masteractorSystem" , config)
    // 通过actorSystem获取一个actor
    val sparkMaster : ActorRef = masteractorSystem.actorOf(Props(new SparkMaster) , "sparkMaster")

  }
}