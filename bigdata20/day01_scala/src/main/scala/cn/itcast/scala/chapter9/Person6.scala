package cn.itcast.scala.chapter9

/*
protected : 访问权限声明关键字,本类和同文件(同包)下的,还有子孙类可以访问,其它类都不能访问.
.*/

class Person6 {
  protected var name : String = "tom"
  protected [this] var hobby : String = "game"
  protected def sayBye = println("再见...")
}

class Student7 extends Person6{

def sayHello = println("Hello" + name)
def sayByeBye = sayBye
def makeFriends(s:Student7) = {
  println("My hobby is " + hobby + ", your hobby is Unkown")
}
}


object Student7{
def main(args: Array[String]): Unit = {

val s : Student7 = new Student7
    s.sayHello
    s.makeFriends(s)
    s.sayByeBye
}
}