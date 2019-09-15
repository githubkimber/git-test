package cn.itcast.scala.chapter9

/*
子类要继承父类中的一个非抽象方法,必须使用 override 关键字,
如果在子类中调用父类中被覆盖的方法,必须使用 super 关键字;
*/

class Person1 {

  private val name = "leo"
  val age = 50

  def getName = this.name

}

class Student1 extends Person1 {

  private val score = "A"
  def getScore = this.score

  override
  val age = 30
  override  def getName = "your name is" + super.getName






}
