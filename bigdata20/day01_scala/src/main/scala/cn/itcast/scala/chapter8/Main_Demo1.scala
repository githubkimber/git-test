package cn.itcast.scala.chapter8

/*
同java一样, 如果要运行一个程序, 必须要编写一个包含main方法的类:
在Scala中, 也必须要有一个main方法, 作为入口;

Scala中的main方法定义为 def main (args : Array[String]), 而且必须定义在object中;
除了自己实现main方法之外,还可以继承AppTrait, 然后, 将要写在main方法中运行的代码,
直接作为object的constructor代码即可,而且还可以使用args接受传入参数:
*/

object MainDemo1 {

def main(args: Array[String]): Unit = {
    println("我是main方法")
  }
}


// MainDemo2 类之所以能执行是因为继承了App类,App类中就有main方法,
// MainDemo2 类的程序通过父类App的main方法得到了执行;
object MainDemo2 extends App{
  println("我是第一行代码")
  println("我是第二行代码")
}