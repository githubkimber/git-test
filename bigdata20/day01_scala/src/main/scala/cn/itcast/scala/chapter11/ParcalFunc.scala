package cn.itcast.scala.chapter11

/*
偏函数: 被包括在花括号内没有match的一组case语句是一个偏函数, 它是PartialFunction[A,B]的
一个实例, A代表参数输入类型, B代表返回结果类型, 常用作输入模式匹配, 偏函数输入的最大特点
就是它只接受和处理其参数定义域的一个子集
*/

object ParcalFunc {

val func1 : PartialFunction[String, Int] = {  // 偏函数: 调用给参数就可以匹配case,返回值
  case "one" => 1
  case "two" => 2
}

def func2(num: String) : Int = num match{   // 也是偏函数
  case "one" => 1
  case "two" => 2
  case _ => -1
}

def main(args: Array[String]): Unit = {

println(func1("one"))
println(func2("one"))
println(func1.isDefinedAt("three"))   // 判断func1中是否有 case "three" 匹配项
if (func1.isDefinedAt("three")){
  println("有three")
}else{
  println("没有three")
}
}


















}
