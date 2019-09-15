package cn.itcast.scala.chapter10

/*
Scala中trait也可以继承class,此时这个class就会成为所有继承该trait的子类的超级父类
*/

class MyUtil {
def printMsg(msg : String) = println(msg)
}

trait Logger_two extends MyUtil {
  // 调用父类方法,不是重写
  def log(msg : String) = this.printMsg("log"+msg)
}

class Person_Three extends Logger_two {
  def sayHello {
    this.log{"class extends Trait"}
    this.printMsg("hello I am printMsg")
  }
}

object Person_Three {
  def main(args: Array[String]): Unit = {
    val three = new Person_Three
    three.sayHello
  }
}

/*    class extends Trait
logclass extends Trait
hello I am printMsg
 */