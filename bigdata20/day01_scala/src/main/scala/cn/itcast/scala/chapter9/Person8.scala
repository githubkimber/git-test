package cn.itcast.scala.chapter9

/* 如果在父类中, 有某些方法无法立即实现,而需要依赖不同的子类来覆盖,重写实现不同的方法.
此时,可以将父类中的这些方法编写方法编写成只含有方法签名,不含方法体的形式,这种形式就叫做抽象方法.

一个类中,如果含有一个抽象方法或抽象field,就必须使用 abstract 将类声明为抽象类,该类是不可以被实例化的

在子类中覆盖抽象类的抽象方法时, 可以不加 override关键字.
*/

abstract class Person8(val name : String) {

// 抽象类定义方法: def 方法名 : 返回值类型
def sayHello : String
def sayBye : String

// 如果在父类中,定义了field,但是没有给出初始值,则此field为抽象field
var age : Int
val aad :String
}

class Student9(name : String) extends Person8("张三") {

override
def sayHello: String = {
"sayHello"
}
override
def sayBye: String = {
"sayBye"
}

 val aad: String =""
 var age: Int = 12
}

object Student9{
  def main(args: Array[String]): Unit = {

    val lisi = new Student9("李四")
    val bye = lisi.sayBye
    val hello = lisi.sayHello
    println(bye + hello)
  }
}