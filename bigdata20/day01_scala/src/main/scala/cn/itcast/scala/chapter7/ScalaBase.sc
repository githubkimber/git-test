import com.sun.corba.se.impl.javax.rmi.CORBA.Util

import scala.runtime.Nothing$
import scala.util.control.Breaks

// 申明变量的基本语法
// val/var 变量名 [:变量类型] = 变量值
//申明一个变量, 变量名是  name ,变量类型是 String ,变量值是 张三 .
val name:String = "zhangsan"  // val修饰的关键字不可变(变量常量化)
println("name:"+name)
 // name="lisi"
var age:Int = 30          // 一般使用var定义变量,var修饰的变量可变
println("age:"+age)
age=50
println("age:"+age)

// 类型推断: 如果不定义变量类型,scala会自己根据数据推断类型
var address = "helloworld"
println(address)
var a = "123f"
println(a)

// 块表达式 , 返回值类型取决于我们最后一行代码类型
val block = {
  println("hello worker")
  "hai nice"
  123
  "yrty"
  println(313)   // Unit返回类型,类似java当中void空类型

}

// Null 是所有引用类型的子类
// Nothing 是所有引用类型(包括Null)和值类型的子类

// var c:Int =Nothing$
//var b = Nothing$


val age2 : Int = 30
// 注意 : 这个减号是一个方法 可以通过.调用
println(age2 - 5 )
println(age2.-(23))


val d = 20
val aResult = if ( d < 10 ){
  10
}else if (d > 30){
  30
}else{
  "hello"
}

println(aResult)

// while 循环没有任何返回值类型
var e = 1
val f = while (e <= 10){
    e += 1

}
println(f)
println(f+"=======================")

// 终止循环
var g = 10
val loop = new Breaks  // 做一个对象(标记)
loop.breakable{
  val h = while (g<=20){
    g += 1
    println(g)
    if (g == 15){
      loop.break() //跳出循环
    }
  }
  println(h)
}
println(g+"=======================")

// for循环 (使用to两边均闭合)
for (i <- 1 to 3 ; j <- 1 to 5 ){
  println(i*j)
}
println("to"+"=======================")

// for循环 (使用until前闭后开)
for(i<- 1 until 5 ; j <- 1 until 4){
  println(i*j)
}

for (i<- 1 to 3 if(i != 2) ){
  println("hello worker")
}

// 引入变量
for (i<- 1 to 3 ; j = (i-1)){
  println(i*j)
}

// yield : 把前面的for循环输入到集合中
val for5 = for (i<- 1 to 5) yield i
println(for5)

for {
  i <- 1 to 5
  j = 5 - i
}
println(i*j+"myij")


println("函数=========================")
import scala.math._
// sqrt() 求平方根(返回值double类型)
sqrt(100)

// 生成随机数
BigInt.probablePrime(3,scala.util.Random)

// 去处重复字符
"helloworker".distinct

// 根据字符串下标索引截取字符
"helloworker"(4)    // 等价于
"helloworker".apply(4)

Array(e,g,a)    // 等价于
Array.apply(2,d,0)

val arr1 = new Array[Int](5)
arr1(1) = 2
arr1.update(1,2)
// mkString(",")使用分隔符","
println(arr1.mkString(","))

/*方法定义的形式:
  def 方法名 (参数1: 参数类型1, 参数2: 参数类型2): 返回值类型 = {
方法体
}

需求 : 定义一个方法, 方法名是hello, 两个参数, int和String类型,
返回值int类相关.
*/
def hello (first : Int , second: String) : Int ={

  first
}
var l = hello (12, "kjhi")
println("l:"+l)

// 不定义返回值类型
def hello2(first: Int , second : String) = {

  "helloworker"
}
var k = hello2(30,"zhangsan")
 println("k:"+k)

// 不定义返回值类型,让其自己推断类型
def hello3 (first : Int, second:String) = {
  if (first > 10 ){
    first
  }else{
    second
  }
}
var hello3Result = hello3 (5, "helloworker")
println(hello3Result)
hello3Result = hello3(second = "jhgkyf" , first = 8)
println(hello3Result)


def hello4(first : Int = 20 , second : String ) = {
  println(second)
  println(first)
}
hello4(second = "list")


// 函数的变长参数
def hello5(first:Int * ):Int = {
  for (i<- first) {
    println(i)
  }
  12
  }
hello5(1,2,3,4,5)

// 递归函数
def hello6(first : Int) : Int = {
  if (first < 1){
    1
  }else{
    first * hello6(first - 1)
  }
}

val hello6Result = hello6(10)
println(hello6Result)

// 方法中如果没有等于号,那么这个方法强调的是方法体当中代码的执行(没有返回值)
def hello7(first:Int, second:String){
  first
}
println(hello7(20 , "zhangsan"))

def hello8() = {"abc"}
def hello8new() = "abc"
def hello8new2 = "abc"  // 定义方法: def
var hello8new3 = "abc"  // 定义变量: var / val

// 定义方法可以省略大括号
def hello10 (first : Int , second : Int ) = first +second
val hello10Result = hello10(10 , 20)
println (hello10Result)


/* 函数定义的两种形式
第一种形式:
val 函数名 = (参数名1 : 参数类型1 , 参数名2 : 参数类型2 ) => {函数体
}

第二种形式:
val 函数名 : (参数类型1 , 参数类型2) => (返回类型) = {
    函数体
}

    需求 ===> 使用第一种定义方式, 定义一个函数名 func1 ,参数1名: first
    参数1类型: Int {函数体返回参数1}
 */
// 调用函数,直接使用函数名(参数)
val func1 = (first : Int) => {
  1+first

}
 var m = func1(2)
println("m:"+m)

// 匿名函数, 没有名字的函数(没法直接调用)
(x: Int , y:Int) => {
  x * y
}

/*
需求: 定义函数 func3 , 参数1类型: Int , 参数2类型: String ,
返回值类型: String .
 */
val func3 : (Int , String) => (String) = {
  (x , y) =>            // 必须接受参数(否则报错)
    var a ="sjask"
    var b = "123"
  a+= 165
  a
  b
}
println("返回值:"+func3(1,"3"))    // 结论: 返回值为函数体最后一行


// 方法就是函数, 函数也是对象
def hello8(first:Int, second:String):Int={
  1
}

val myfunc = (first : Int , second : String) => {

}

// 第一种函数定义形式
val myFunc1 = (x: Int) => {
  x * x
}
// 第二种函数定义形式
val myFunc2 : Int => Int = {
  x => x * x
}

// (在方法中)函数可以作为参数传递
def methodFunc(f:Int => Int):Int = {
  f(100)
}
methodFunc(myFunc2)
methodFunc(myFunc1)

// (在方法中)方法不能作为参数传递,必须先转换成一个函数再传递
def method2(x : Int) = {x * x}
def methodFunc2(x : Int => Int) : Int = {
  x (100)
}

val methodFunc2Result = methodFunc2(method2)
println(methodFunc2Result)

// 可以通过 _ 自动的将方法转换成函数
def method3 (x : Int , y : String) = {
  x
}
println(method3 _)

// lazy  懒值加载 : 声明的值不会马上分配内存空间,等到你要调用的的时候,
            // 再去计算
def init() : String = {
  println("init方法执行")
  "嘿嘿嘿,喵喵喵~"
}
lazy val msg = init()
println("lazy方法没有执行")
println(msg)

println("=================================")
def initl() : String = {
  println("init方法执行")
  "嘿嘿嘿,喵喵喵~"
}
val msgl = initl()
println("lazy方法没有执行")
println(msgl)