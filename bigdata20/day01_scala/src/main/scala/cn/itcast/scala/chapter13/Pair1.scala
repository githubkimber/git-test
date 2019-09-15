package cn.itcast.scala.chapter13

/*
Scala中可以通过上下界, 指定泛型是某个类型的子类型, 后者泛型是某个类相关的父类型, 也就是限制泛型的类型
extends T  就表示: 泛型, 必须是T类型的子类, 这种情况叫做上界
super  T  就表示 : 泛型, 必须是T类型的父类, 这种情况叫做下界

<: 来限定: 我们传入的泛型,

compareTo() :该方法用于两个相同数据类型的比较，两个不同类型的数据不能用此方法来比较。
如果指定的数与参数相等返回0 ,如果指定的数小于参数返回 -1 ,如果指定的数大于参数返回 1 .
Int类型没有CompareTo方法,只有父类RichInt才有
*/

class Pairl[T <: Comparable[T]](val first : T , val second : T){

def smaller = if (first.compareTo(second) < 0) first else second
}

object Main1 extends App{
  override def main(args: Array[String]): Unit = {

   val p = new Pairl("hello" , "Brooks")
    println(p.smaller)
  }
}