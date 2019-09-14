package cn.itcast.scala.chapter16

class Man

class SuperMan {
  def beat = println("超人打怪兽")
}

object SuperMan{

  // 隐式转换方法
  implicit def mantoSuperMan(man : Man) = new SuperMan

  def main(args: Array[String]): Unit = {

    val man = new Man
    man.beat
  }
}
