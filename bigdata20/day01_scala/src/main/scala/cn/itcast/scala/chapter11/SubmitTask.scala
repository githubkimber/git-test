package cn.itcast.scala.chapter11

/*
样例类首先是类, 除此之外他是为模式匹配而优化的类, 样例类用case关键字进行声明,
样例类主要是使用在我们后面的sparkSql当中, 通过样例类来映射我们表中的对象

定义形式: case clase 类型   ,是多例的,后面要跟构造参数:  如:  case class Student(name : String)
         case object  类型  ,是单例的;   如:  case object Person
*/
import scala.util.Random

case class SubmitTask (id : String, name : String )
case class HeartBeat (time : Long)
case object CheckTimeOutTask

object CaseDemoO4 extends App {
  val arr = Array(CheckTimeOutTask, HeartBeat(12333), SubmitTask("0001", "task-0001"))

  val arrayResult = arr(Random.nextInt(arr.length))   // 随机生成arr数组的下标
  arrayResult match {
    case CheckTimeOutTask => println("匹配上了CheckTimeOut")
    case HeartBeat(x) => println("心跳时间: " + x)
    case SubmitTask(x, y) => println(s"$x...$y")
  }

  arr(2) match {
    case SubmitTask(id, name) => {// 满足条件: 括号内的全执行
      println(s"$id,$name")
      println(id)
      println(name)
      println(id + "\t" + name)
    }
  }
}
