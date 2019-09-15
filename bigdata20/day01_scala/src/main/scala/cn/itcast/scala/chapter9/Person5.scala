package cn.itcast.scala.chapter9

class Person5 {}
class Student5 extends Person5
class Student6 {}
object Student5{
  def main(args: Array[String]): Unit = {

val p : Person5 = new Student5
val p2 : Student6 = new Student6

p match {

// 匹配是否为Person类或其子类对象
case per: Person5 => println("This is a Person5's Object!")
// "  _ " 通配符: 匹配所有剩余的情况
case _ => println("Unknown type!")

}

p2 match {
  // 匹配是否为Person类或其子类对象
  case per: Person5 => println("This is a Person5's Object!")

  case per : Student6 => println("This is a Student6's Object!")
  // "  _ " 通配符: 匹配所有剩余的情况
  case _ => println("Unknown type!")
}
}
}