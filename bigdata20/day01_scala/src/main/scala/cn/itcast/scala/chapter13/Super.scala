package cn.itcast.scala.chapter13

/*
class Anmial
class Dog extends Anmial

List[Dog] extends List[Anmial]  协变: +

List[Anmial] extends List[dog]  逆变: -

List[Dog] = List[Dog]   非变(不变)   无符号
*/

class Super
class Sub extends Super
// 协变
class Temp1 [+A](title : String)
// 逆变
class Temp2 [-A](title : String)
// 非变
class Temp3 [A](title : String)

object Covariance_demo{
  def main(args: Array[String]): Unit = {

// 协变
    val t1 : Temp1[Super] = new Temp1[Sub]("hello scala!!!")
// 逆变
    val t2 : Temp2[Sub] = new Temp2[Super]("hello scala!!!")

    println(t1.toString)
    println(t2.toString)
  }
}