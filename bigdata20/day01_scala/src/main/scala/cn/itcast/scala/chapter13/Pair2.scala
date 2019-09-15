package cn.itcast.scala.chapter13

/*
<: 来限定: 我们传入的泛型,

replaceFirst() :该方法使用给定的参数,替换字符串第一个匹配给定的正则表达式的子字符串。
*/

class Pair2[T](val first : T, val second : T) {

  def replaceFirst[R >: T](newFirst : R) = new  Pair2[R](newFirst, second)
  override def toString = "(" + first + "," + second + ")"
}

object Main2 extends App{
  override def main(args: Array[String]): Unit = {

    val p = new Pair2("Nick", "Alice")
    println(p)
    println(p.replaceFirst("Joke"))
    println(p)
  }
}