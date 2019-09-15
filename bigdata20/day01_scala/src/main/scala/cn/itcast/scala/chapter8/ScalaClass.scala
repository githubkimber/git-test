package cn.itcast.scala.chapter8

object ScalaClass {

def main (args: Array[String]) : Unit = {

val person = new Person
val name : String = person.name
val age : Int = person.age
println("name:"+name)
println("age:"+age)
println(person.hello(120,"zhangsan"))

// 改变Person类中var修饰的值
person.age = (30)
println("age:"+person.age)

// 给Person类的字段赋值操作: .age = 22
  person.age = 20
  person.age= 21
  println("age:"+person.age)

// Person类的字段调用set赋值操作: .age_= ( age_=之间不能有空格, 之必须用小括号)
person.age_= (22)
person. age_=(23)
println("age:"+person.age)


// 通过构造器创造类
val mhd = new Dog("小黑狗" , 3)
val mwd = new Dog("小白狗" , 2 , "bzd")
// val mhuid = new Dog("小灰狗" , 2 , "hui" , "xhd")     因为该构造器已经私有化了








}
}
