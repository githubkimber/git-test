package cn.itcast.scala.chapter9

/*
继承关键字: extends
继承: 子类可以继承父类的field(字段)和method(方法), 而且子类还可以在自己内部实现父类没有的,
      子类可以覆盖父类的field和method,但是如果父类用 final 修饰method 和 field ,则该
      method 和 field 是无法被继承的,如果其类声明用 final 修饰,则该类无法被继承.

private 修饰的field 和method 不能被子类继承, 只能在本类和伴生类或伴生对象中使用;

field 必须要被定义成val 的形式才能被继承, 并还要使用override 关键字. 因为var 修饰的field 是可变的,
在子类中可直接引用被赋值, 不需要被继承;

 继承 就是改变覆盖的意思.


子类要继承父类中的一个非抽象方法,必须使用 override 关键字,
如果在子类中调用父类中被覆盖的方法,必须使用 super 关键字;


*/

class Person {

val name = "张三"

def getName = this.name

}


class Student extends Person{

  // 通过 override 关键字来继承我们父类的field或method(这并不会改变父类中的值)
  override
  val name = "list"

//  override def getName : String ={
//    "jhiu"
//  }

  override   def getName : String = super.getName

  // 也可以定义自己的field和method
  val age = 20
  def getMyAge = this.age
}