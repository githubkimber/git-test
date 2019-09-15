package cn.itcast.scala.chapter14

import scala.actors.Actor

class MyActor3 extends Actor {
  override def act(): Unit = {

// 每次来一条消息都会启动一个线程去执行, 线程开销太大, 可以使用react方法来代替, react会复用线程,不会反复的创建线程.
    while (true){
      receive{
        case "start" => println("start....")
        case "end" => println("end....")
      }
    }
  }
}

object MyActor3{
  def apply(): MyActor3 = new MyActor3()    // 伴生对象,伴生类方式获取对象
  def main(args: Array[String]): Unit = {
    val actor = MyActor3()               // 一般方式
    actor.start()                      // 伴生对象,伴生类方式调用
    actor ! "start"                         // 伴生对象,伴生类方式调用   结果: 打印不出来; 原因: myActor3由方法产生,每次使用都会重新调用方法产生新对象;
    actor ! "end"
  }
}
