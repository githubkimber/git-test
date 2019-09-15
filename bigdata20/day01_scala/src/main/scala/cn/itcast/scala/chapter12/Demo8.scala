package cn.itcast.scala.chapter12

/*
定义一个class类, 接受两个参数, 但是两个参数都是泛型,泛型的类型, 会根据我们创建类的
实例化对象的时候, 动态的传递进行动态的推断
*/

object Demo8 {
  def main(args: Array[String]): Unit = {
    val result1 = new MyClass("hello", 50)
    val result2 = new MyClass[Any, Any]("zhangsan", "lisi")
  }

}

class MyClass[T, B](first : T, second : B){
  println(first+","+second)
}