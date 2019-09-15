package cn.itcast.scala.chapter14

import scala.actors.Actor

class Actor1 extends Actor{

  override def act(): Unit = {
    for(x <- 1 to 10){
      println(x)
    }
  }
}

object Actor2 extends Actor{

  override def act () : Unit = {
    for (j <- 1 to 10){
      println("j" + j)
    }
  }
}

object Actor1{
  def main(args: Array[String]): Unit = {

    val actor = new Actor1
    actor.act()
    Actor2.act()
  }
}