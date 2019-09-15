package cn.itcast.scala.chapter11

/*
:: 该方法被称为cons，意为构造，向队列的头部追加数据，创造新的列表。用法为 x::list,其中x为加入到头部的元素，无论x是列表与否，它都只将成为新生成列表的第一个元素，也就是说新生成的列表长度为list的长度＋1(btw,x::list等价于list.::(x))

:+和+: 两者的区别在于:+方法用于在尾部追加元素，+:方法用于在头部追加元素，和::很类似，但是::可以用于pattern match ，而+:则不行. 关于+:和:+,只要记住冒号永远靠近集合类型就OK了。

++ 该方法用于连接两个集合，list1++list2

::: 该方法只能用于连接两个List类型的集合
*/

/*
match(匹配) : 模式匹配当中匹配类型
*/

object CaseObject {
  def main(args: Array[String]): Unit = {
    val a = 5
    val obj = if (a == 1) 1
    else if (a == 2) "2"
    else if (a == 3) BigInt(3)
    else if (a == 4) Map("aa" -> 1)
    else if (a == 5) Map(1 -> "aa")
    else if (a == 6) Array(1, 2, 3)
    else if (a == 7) Array("aa", 1)
    else if (a == 8) Array("aa")


    val matchResult = obj match {
      case x: Int => x
      case s: String => s
      case BigInt => 3 // 这种方式匹配不到
      case m1: Map[String, Int] => "map[String,Int]" // 泛型的擦除,除了Array不会被擦除,其他的集合类型都会被擦除
      case m2: Map[Int, String] => "map[Int,String]"
      case a1: Array[Int] => "array[Int]"
      case a2: Array[Any] => "array[Any]"
      case a3: Array[String] => "array[String]" // String类型是Any类型子类,放在Any后面会被Any覆盖.
      case _ => "没有匹配项"
    }

    println("matchResult: " + matchResult)
  }
}

object CaseObject1 {
def main(args: Array[String]): Unit = {

val myArray = Array(1,2,2)
val matchResult1 = myArray match {
  case Array(0, x) => x
  case Array(1, x, y) => x + y
  case Array(0, _*) => "0..." // Array当中必须以0开头,后面有多少点不重要
  case _ => "没有匹配项"
}
  println("matchResult1: "+matchResult1)

val lst = List(3, -1 )
val matchResult2 = lst match{
  case 3::Nil => println("列表就一个元素3")    // :: 该方法被称为cons，意为构造，向队列的头部追加数据，创造新的列表; Nil :为空集合
  case 3::(-1)::Nil => println("列表有元素'3','-1'")
  case x::y::Nil => println(s"$x+$y")     //  s"$x+$y"  :Scala当中的特有的一种字符拼接方式;
  case _ => println("something else")
}
  println("matchResult2: "+matchResult2)

val tup = (0, 3, 5)
val matchResult3 = tup match {
  case (1, x, y) => println(s"1, $x, $y")
  case (_, z, 5) => println(z)
  case _ => println("else")
}
println("matchResult3: "+matchResult3)
}
}
