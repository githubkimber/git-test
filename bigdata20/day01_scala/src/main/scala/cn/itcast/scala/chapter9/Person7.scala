package cn.itcast.scala.chapter9

import com.sun.jndi.cosnaming.IiopUrl.Address

/*
Scala 中,每个类都可以有一个主 constructor(构造函数) 和任意多个辅助 constructor, 而且每个辅助
构造器的第一行都必须调用其他辅助构造器或者主构造器代码;

因此子类的辅助构造器是一定不可能直接调用父类的构造器的;
只能在子类的主构造器中调用父类的构造器;

如果父类的构造器已经定义过field(字段),比如name和age,子类再使用时,就不要用val或var来修饰了,
否则会被认为,子类要覆盖父类的field,且要求一定要使用override关键字
*/

class Person7 (val name : String , val age : Int){
  println("主构造器Person7-age执行了")
var score : Double = 0.0
var address : String = "beijing"

def this(name : String , score : Double){
  this(name , 30)
  this.score = score
  println("构造器Person7-score执行了")
}

def this (name: String , address: String){
  this(name , 100.0)
  this.address = address
  println("构造器Person7-address执行了")
}
}

// 如果我们继承的class类没有空的构造器,那么在继承的时候,必须将参数传过去
class Student8(name : String , score : Double) extends Person7("abc" , 2.0){
  println("主构造器Student8-score执行了")

var myaddress = "上海"

// 注意: 子类的辅助构造器, 只能够调用子类的主构造器,或者子类的辅助构造器
//       不能够调用父类的辅助构造器或父类的主构造器

def this (name : String , myaddress : String){
  this(name , 12.2)
  println("构造器Student8-myaddress执行了")
}
}


object Student8{

def main(args: Array[String]): Unit = {

val zhangsan = new Student8("张三" , "李四")      // 调用的是 构造器Student8-myaddress
println(zhangsan)
}
}