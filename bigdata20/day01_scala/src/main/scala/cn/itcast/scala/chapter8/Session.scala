package cn.itcast.scala.chapter8

//object Session {
  /*
在Scala当中, 没有类似于像java当中的static修饰的静态属性或者静态方法或者静态代码块之类的,

但是我们可以通过Scala当中的 Object 来实现类是的功能,可以理解为Scala当中的 Object 里面的属性或者方法
都是静态的,可以直接调用.
*/

//  def helloSession(first: Int): Int = {
//    first
//  }
//}

//object SessionFactory {
//  def mian(args: Array[String]): Unit = {
//    Session.helloSession(20) // Session 类是object静态的可以直接调
//  }
//}


class Session {

def helloSession(first: Int): Int = {
    first
}
}


object SessionFactory1 {

  val session = new Session
def getSession : Session = {
  val session1 = new Session
  session1
  session
}

def main(args: Array[String]): Unit = {
val session = new Session

for (x <- 1 to 10) {
val mySession: Session = SessionFactory1.getSession
println(mySession)
}
}
}










