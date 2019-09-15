package cn.itcast.scala.chapter12

object MethodType {
  def getMiddle[T](canshu: T) = {
    canshu
  }


  def main(args: Array[String]): Unit = {

    println(getMiddle(Array("Bob", "had", "a", "little", "brother")).getClass.getTypeName)

    val f = getMiddle[String] _
    println(f("Bob"))
  }
}