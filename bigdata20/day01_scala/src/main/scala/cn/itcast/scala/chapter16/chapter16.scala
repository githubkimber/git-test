package cn.itcast.scala.chapter16

/*
implicit :关键字只能用来修饰方法, 变量 (参数)

隐式转换的方法在当前范围内才有效, 如果隐式转换不在当前范围内定义(比如定义在另一个类中或包含在某个对象中),
那么必须通过import语句导入.

所有隐式值和隐式方法必须放到object中才能生效.

隐式参数: 两个范围: 当前作用域内可见的val或var定义的隐式变量;
                   一种是隐式参数类型的伴生对象内的隐式值;

隐式转换的时机:
1.当对象调用类中不存在的方法或成员时, 编译器会自动将对象进行隐式转换;
2.当方法中的参数的类型与目标类型不一致时;
*/


// 案例1>.将Double类型转自动换成Int类型

object chapter16 {
  // 定义一个隐式转换的方法必须在object中,
  implicit def DoubleToInt(first : Double) = first.toInt

}

object Convert{
  // 导入隐式转换方法
import chapter16.DoubleToInt

  def main(args: Array[String]): Unit = {

    val myInt : Int = 3.5
    println(myInt)
  }
}