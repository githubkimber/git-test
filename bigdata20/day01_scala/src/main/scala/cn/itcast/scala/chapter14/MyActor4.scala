package cn.itcast.scala.chapter14

import scala.actors.Actor

/*
如果使用react的方式来进行接收数据,如果需要反复不断的接收数据,那么就使用loop
*/

class MyActor4 extends Actor{
  override def act(): Unit = {

   loop{
     react{
       case "start" => println("start....")
       case "end" => println("end")
     }
   }
  }
}

object MyActor4{
  def main(args: Array[String]): Unit = {
    val actor = new MyActor4
    actor.start()
    actor ! "start"
    actor ! "end"
  }
}