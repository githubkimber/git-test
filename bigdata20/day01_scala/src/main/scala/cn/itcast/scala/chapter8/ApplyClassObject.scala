package cn.itcast.scala.chapter8

class ApplyClassObject(name:String) {
  println(name)
}

object ApplyClassObject{

def apply(name: String): ApplyClassObject = new ApplyClassObject(name)

def main(args: Array[String]): Unit = {

  val applyClassObject = new ApplyClassObject("张三")

  val applyClassObject1 = ApplyClassObject("李四")


// new Array(...) 数组底层还是调用了Apply方法, Apply方法内部就有封装Array数组();
  val myArray = Array(1,2,3,4,5)      // 定义一个数组
  val myArray1 = new Array[Int](10)   // 定义一个长度为10的数组
  myArray(0) = 1
  myArray(0) = 1

}
}