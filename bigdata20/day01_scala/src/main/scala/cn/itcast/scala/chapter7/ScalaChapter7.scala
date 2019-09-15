package cn.itcast.scala.chapter7

object ScalaChapter71{

  /*
  当中程序的入口类main, 一定要写在Object里面
   */
   def main (arg: Array[String]): Unit = {

// 方法中使用函数作为参数, 这样的方法叫高阶函数
val m1 = (x : Int) => (x * x)

val m2 = Array(1,3,5,7,9).map(x => x * x)   // 相当于(x:Int => x*x)的简写
println("m2:"+m2.mkString(","))

// 如果集合当中每个元素传入到函数里面去只用了一次那么就可以用 _ 代替,两次及以上则不行
val m3 = Array(1,3,5,7,9).map(_*2)     // 相当于 (x => x * 2)
println("m3:"+m3.mkString(","))

// 匿名函数 有几个参数结果就是function几
 println("匿名函数:"+((x : Int, y : Int) => {x * y}))


// 1.1定义一个函数 m4
val m4 : (String , Int) => (Int , String) = {
  (x , y) => (y , x)                    // 函数体内必须承接参数
}
// 1.2定义一个方法 m5
def m5( hello : (String , Int) => (Int , String) ) : Int = {
//  hello("zhangsan", 60)               // 方法体内可以不用承接参数
  32
  hello("zhangsan", 60)._1              // ._通过索引获取元组的元素
}
// 1.3//使用函数作为方法的参数
val m6 = m5(m4)
println("m6:"+m6)

// 高阶函数(方法)返回类型也可以是函数
def m8(x : Int ) = {"当方法体只有一句执行语句,外面大括号可以省略"}
//(y : String ) => y      匿名函数

def m7(x : Int ) = (y : String ) => y
     println("m7(50):"+m7(50))

// 参数的类型推断 map方法:引用自定义函数(一个参数)   def map[B, That](f: A => B)(implicit bf: CanBuildFrom[Repr, B, That]): That = {
val m9 = Array(1,2,3,4,5)
   m9.map((x : Int) => x * 2) //简化于下面
println("m9:"+m9.mkString(","))
   m9.map(( x ) => x * 2) //简化于下面
   m9.map( x => x * 2) //简化于下面 函数里面去只用了一次那么就可以用 _ 代替,两次及以上则不行
val m10 = m9.map( _ * 2) //简化于下面
println("m10:"+m10.mkString(","))


// 柯里化是Scala面向函数式编程,必然导致的一种结果.
// 柯里化定义形式: 参数分两个小括号
def kery(x : Int)(y : Int) : Int = {
  x + y
}
// 柯里化调用形式
val kery1 : Int = kery(30)(20)
     println("kery1:"+kery1)

// 柯里化的推导过程
def kery2(x : Int) = {
  y : Int => y + x    // 匿名函数
}
def kery3(x : Int) = ( y: Int) => y + x // 只有一个执行语句是外面的大括号可以省略
def kery4(x : Int)( y: Int) = y + x
     println("kery2:"+kery3(30)(20))

val kery5 = (x : Int) => {
  (y : Int) => (x + y)
}

val kery6 = (x : Int) => (y : Int) => (x + y)


def kery7(x : Int) = {
  y : Int => y + x
}

// map(kery7(20)) =>>   y :Int => y + 20
//  =>> 1,3,5,7,9 =>   y :Int => y + 20
// kery8 = 得到: 21,23,25,27,29
val kery8 = Array(1,3,5,7,9).map(kery7(20))
println("kery8:"+kery8.mkString(","))
   }
}
