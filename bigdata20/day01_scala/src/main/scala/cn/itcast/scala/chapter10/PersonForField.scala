package cn.itcast.scala.chapter10

/*
Scala中的trait可以定义具体的field,此时继承trait的子类就自动获得了trait中定义的fieid;
但是这种获取field的方式与继承class的是不同的.如果是继承class获取的field,实际上还是定义在父类中的;
而继承trait获取的field,就直接被添加到子类中了
*/

trait PersonForField {

val age : Int = 20
//val address : String
}

// 继承 trait 获取的field直接被添加到子类中
class StudentForField(val name : String) extends PersonForField{

//  override val address: String = _
  def sayHello = println("Hi , I ' m "+ this.name+" , my age is "+ age)
}

object StudentForField{
  def main(args: Array[String]): Unit = {
    val s = new StudentForField("tom")
    s.sayHello
  }
}

