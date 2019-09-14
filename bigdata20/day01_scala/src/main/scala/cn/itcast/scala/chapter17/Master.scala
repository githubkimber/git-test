package cn.itcast.scala.chapter17

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import com.typesafe.config.{Config, ConfigFactory}

class Master extends Actor {
println("我是构造器, 最先调用")

  override def preStart(): Unit = {
    println("我是一个初始化方法, master对象调用完成后, 就会调用我")
  }

// receive :(接受)方法会不断的被调用, 一旦有消息, 就会收的到
// 类似于 loop{reat{}}
override def receive: Receive = {
  // 模式匹配
  case "connect" => {
    println(" a client connected ")
    sender ! "success !"
}
}
}

object Master{
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
    val actorSystem = ActorSystem("actorSystem" , config)
    // 创建master的actor,并且给master的actor起一个名字叫做master
    // Props 需要传入Actor的子类,Master就是Actor的子类
    val master : ActorRef = actorSystem.actorOf(Props(new Master) , "master")
    // 不需要调用start, 拿到数据直接发送即可.
//    master ! "connect"
  }
}