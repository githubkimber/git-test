package cn.itcast.scala.chapter8

/*
如果有一个class文件, 还有一个与class同名的object文件,那么就称这个object是class的伴生对象,
class是object的伴生类;
伴生类和伴生对象,class是object的伴生类;
伴生类和伴生对象必须存放在一个.scala的文件;
伴生类和伴生对象的最大特点是,可以相互访问;
*/


class ClassObject {

val id = 1
private var name = "itcast"
  def printname() : Unit = {
// 在伴生类中可以访问伴生对象的私有属性
    println(ClassObject.CONSTANT + name)
  }

}


object ClassObject{

// 伴生对象中的私有属性
private val CONSTANT = "汪汪汪 : "

def main(args: Array[String]): Unit = {
val p = new ClassObject
// 访问伴生类的私有字段name
p.name = "123"
p.printname()

}

  def apply : ClassObject = new ClassObject()

}