package cn.itcast.scala.chapter16

class A(c : C) {
  def readBook(first : String) : Unit = {
    println("A说 : 好书好书...")
  }
}

class B(c : C){
  def readBook() : Unit = {
    println("B说 : 看不懂...")
  }
  def writeBook() : Unit = {
    println("B说 : 不会写...")
  }
}

class C

// 创建一个隐式类
object AB{
implicit  def C2A(c : C) = new A(c)
implicit def C2B(c : C) = new B(c)
}

object B{
  def main (args: Array[String] ): Unit = {
    def apply(c: C): B = new B(c)

  import AB._   // 导入AB类下所有的隐式转换
//  import AB.C2B
  val c = new C

//    c.readBook()
 c.writeBook()
 apply(c: C).writeBook()

}
}