package cn.itcast.scala.chapter9

class Person3 {

/*
Class B extends Class A
Class C

java中: 只提供了子类判断是否属于父类型的方法(instanceOf), 没有提供判断一个类是否是
另外一个类的父类的方法:
B instanceOf A => 如果判断 B是A的子类 (A)B
C instanceOf A => 如果判断 C不是A的子类 不能强转;

Scala 中:
isInstanceOf  类似于java中的instanceOf做类型判断
如果判断为: true ,就可以用 asInstanceOf 做类型转换;
B instanceOf A => 如果判断 B是A的子类 B asInstanceOf A


*/

}

class Student3 extends Person3(

)

object Person3Main {

  def main(args: Array[String]): Unit = {

    val s: Student3 = null // s 没有实例化
    val p: Student3 = new Student3

    println("s.isInstanceOf[Student3]: " + s.isInstanceOf[Person3])       // s 没有实例化向上转型失败
    println("p.isInstanceOf[Person3]: " + p.isInstanceOf[Person3])

    val q : Person3 = new Student3              // 父类型变量能接受子类型对象, 子类型变量却不能接受父类型对象
//  val m : Student3 = new Person3        报错
    println("q.isInstanceOf[Student3]: " + q.isInstanceOf[Student3])

    val l  = new Person3
   println("l.isInstanceOf[Student3]: "+ l.isInstanceOf[Student3])

  }
}