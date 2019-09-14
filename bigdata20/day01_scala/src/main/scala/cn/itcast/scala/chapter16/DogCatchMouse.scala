package cn.itcast.scala.chapter16

object DogCatchMouse {

  // 猫类为狗类所用
  implicit def DogToCat(first : Dog) = new Cat

  def main(args: Array[String]): Unit = {

  val dog = new Dog
  val wangwangwang1 : Unit = dog.wangwangwang
  dog.catchMouse("小黄狗")

  val cat = new Cat
  cat.catchMouse("小花猫")
}
}

class Cat{

  def catchMouse(name : String) = {
    println(name+"抓老鼠")
  }
}

class Dog{
  def wangwangwang = {
    println("小黑狗汪汪汪")
  }
}