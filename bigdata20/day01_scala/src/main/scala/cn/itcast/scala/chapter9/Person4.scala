package cn.itcast.scala.chapter9

class Person4 {}

class Student4 extends Person4{}
class Student41 extends Person4{}

object Student4{
def main(args: Array[String]): Unit = {

/*
类与类型:
类 : 具体到某一个指定的Class里面去; 判断方法: classOf[类名]
类型 : 可以指代很多类, 包括 父类子类等, 兄弟类之间不行,必须构成父子关系;
类型 : 判断方法: isInstanceOf[类名]  是否同属一类型
*/


val p : Person4 = new Student4
val m : Student4 = new Student4

  // 判断p是否为Person4类的实例
println(p.isInstanceOf[Person4])
  // 判断p的类型是否为Person4类
println(p.getClass == classOf[Person4])
  // 判断p的类型是否为Student4类
println(p.getClass == classOf[Student4])
  // 判断p的类型是否为Student41类
println(p.getClass == classOf[Student41])
  // 判断m的类型是否为Student41类
println(p.getClass == classOf[Student41])










  }
}