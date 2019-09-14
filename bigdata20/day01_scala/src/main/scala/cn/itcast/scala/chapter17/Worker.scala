package cn.itcast.scala.chapter17

import akka.actor.{Actor, ActorRef, ActorSelection, ActorSystem, Props}
import com.typesafe.config.{Config, ConfigFactory}

class Worker extends Actor {
  println("我是构造器, 最先调用")

  override def preStart(): Unit = {
    println("我是一个初始化方法, master对象调用完成后, 就会调用我")

// 在这里实现worker给master发送信息: 通过context上下文对象, 调用actorSelection, 想一个指定的actor发送数据
// 注意akka.tcp : // actorSystem@192.168.132.1:8888  是我们的ActorSystem.
// 通过ActorSystem 创建了一个子Actor, 叫master, 通过 /user/master来寻找我们创建的actor
val master : ActorSelection = context.actorSelection("akka.tcp://actorSystem@192.168.132.1:8888/user/master")
master ! "connect"

  }

  // receive :(接受)方法会不断的被调用, 一旦有消息, 就会收的到
  // 类似于 loop{reat{}}
  override def receive: Receive = {
    // 模式匹配
    case "connect" => println(" a client connected ")
    case "success" => println("successFul")
  }
}

object Worker{
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
    val actorSystem = ActorSystem("actorSystem" , config)
    // 创建master的actor,并且给master的actor起一个名字叫做master
    // Props 需要传入Actor的子类,Master就是Actor的子类
    val worker : ActorRef = actorSystem.actorOf(Props(new Worker) , "Worker")
    // 不需要调用start, 拿到数据直接发送即可.
//    worker ! "connect"
  }
}