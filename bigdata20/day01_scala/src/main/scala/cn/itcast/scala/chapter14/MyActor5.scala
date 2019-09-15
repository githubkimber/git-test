package cn.itcast.scala.chapter14

import scala.actors.{Actor, Future}




// 异步消息
case class AsyncMessage(id : Int , message : String)
// 同步消息
case class SyncMessage(id : Int , message : String)
// 返回消息
case class ReplyMessage(id : Int , message : String)

class MyActor5 extends Actor{
  override def act(): Unit = {

    // 使用react的方式不断的接收数据
    loop{
      react{
        case AsyncMessage(x , y) => {
          println(x + y)
          sender ! ReplyMessage(2, "回应-异步无返回值消息")
        }
        case SyncMessage(x , y) =>  { println(x+y)
          sender ! ReplyMessage(5 , "同步且等待返回值的消息")
        }
      }
    }
  }
}

object MyActor5{
  def main(args: Array[String]): Unit = {

    val actor = new MyActor5
    actor.start()
    // 发送一个异步无返回值的消息
    val Unit : Unit = actor ! AsyncMessage(1 , "异步无返回值消息")
    // 发送一个异步有返回值消息, 返回Future[Any]
    val replyMsg : Future[Any] = actor !! AsyncMessage(2 , "异步有返回值消息")
    // 调用apply方法, 得到我们的返回值
    val apply : Any = replyMsg.apply()
    println(apply)

    // 发送一个同步等待返回值的消息
    val unit1 : Any = actor !? SyncMessage(100 , "同步等待有返回值消息")
    println(unit1)
  }
}
