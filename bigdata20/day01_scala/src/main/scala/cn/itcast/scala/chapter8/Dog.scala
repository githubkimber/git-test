package cn.itcast.scala.chapter8


// Scala 当中的构造器,分为两种, 一种是主构造器,写在类名后面的,
// 一种叫做辅助构造器, 所有的辅助构造器, 在最后都必须调用另外一个构造器.

/**
  * 定义一个class类，主构造器，带有两个参数，一个参数是name，一个参数是age
  * @param name
  * @param age
  *            scala的执行代码，不能直接写在class类包含的括号里面
  *            在scala最后编译之后成为.class文件
  *            将我们所有写在class类括号里面的东西，都编译到主构造器里面去了
  *            class Dog{
  *            public Dog(String name,int age){
  *             println(name)
  *             println(age)
  *            }
  *            }
  */
class Dog(name : String , age : Int ) {

  //在scala当中，可以直接将代码写在class当中，而在java当中，
  //代码必须包含在方法当中。
  //其实在scala当中，虽然你把代码写在了Class类当中，经过编译之后，
  //class类的代码都进入到了主构造器方法当中去了

var gender:String = "";

// this 定义构造器
def this(name:String,age:Int,gender:String){

  //辅助构造器，必须以其他辅助构造器，或者主构造器的调用作为第一句
  this(name:String,age:Int)
 // this("zhangsan",3,"baide","buzhidao")
  this.gender = gender
}

//注意：两个构造器，不能相互调

var color =""
 var address = ""
/**
  * 我们也可以通过private来进行修饰我们的构造器，
  * @param name
  * @param age
  * @param gender
  */
//private def this(name:String , age:Int , color:String , gender:String){
//  this(name:String,age:Int,gender:String)
//  this.color = color
//}


def this(name : String , age : Int , gender : String , address : String){
    this(name:String , age:Int)
    this.gender = gender
}




}
