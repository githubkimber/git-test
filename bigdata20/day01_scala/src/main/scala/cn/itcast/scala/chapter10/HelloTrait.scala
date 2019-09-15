package cn.itcast.scala.chapter10

/*
将trait(特质)作为接口使用,trait与Java中的(interface)非常类似;
在trait中可以定义抽象类方法,就想抽象类中的抽象方法一样,只要不给出方法的方法体即可;

class类可以使用 extends 关键字继承 trait,注意,这里不是implement,而是 extends ,
在Scala中没有implement的概念,无论继承类还是trait,统一都是 extends .

类继承后必须实现其中的抽象方法,实现时,可以不使用 override 关键字.

Scala不支持对类进行多继承,但是支持多重继承trait,使用 with 关键字即可.
*/

trait HelloTrait {

def sayHello : Unit
}

trait MakeFriends{

def makeFriends(ch : Children) : Unit
}

class Children(val name : String) extends HelloTrait with MakeFriends with Cloneable with Serializable{

def sayHello: Unit = println("hello")

def makeFriends(c: Children): Unit = println("hello , my name is "+ this.name+" , your name is " + c.name)
}

object Children{
  def main(args: Array[String]): Unit = {

val zhangsan = new Children("张三")

val hello : Unit = zhangsan.sayHello
val friends : Unit = zhangsan.makeFriends(zhangsan)
}
}