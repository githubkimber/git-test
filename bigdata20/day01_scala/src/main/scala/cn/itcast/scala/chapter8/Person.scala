package cn.itcast.scala.chapter8


class Person {

val name : String = "zhangsan"               // val 修饰的变量不可变
var age : Int = 28
private val address : String = "on the earth"   // private 修饰的字段只能在类的内部或伴生对象中访问
private  [this] val pet = "小强"             // private  [this] 修饰的字段只能在类的内部中访问, 伴生对象也不行

def hello(first : Int , second : String) : Int = {
  println(first+"\t"+second)
  250
}

val func1 = (x : Int , y : Int) => {
  x+y
}











}
