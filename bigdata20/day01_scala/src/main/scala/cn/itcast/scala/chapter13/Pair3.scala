package cn.itcast.scala.chapter13

/*
<%  实现类型的隐式转换

Int类型没有CompareTo方法,只有父类RichInt才有
*/

class Pair3[T <% Comparable[T]](val first : T , val second : T) {

  def smaller = if (first.compareTo(second) < 0) first else second
  override def toString = "(" + first + "," + second + ")"
}

object Main3 extends App{

  val p = new Pair3(4 , 2)
  println(p.smaller)
}