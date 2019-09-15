package cn.itcast.scala.chapter14

import scala.actors.Actor

class MyActor2 extends Actor{

  override def act() : Unit = {
    receive{
      case "start" => println("start....")
    }
  }
}

object MyActor2{
  def main(args: Array[String]): Unit = {
    val actor = new MyActor2
    // 启动actor, 类似于启动一个线程
    actor.start()
    // ! 发送一个异步没有返回值的消息
    actor ! "start"
  }
}