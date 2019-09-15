package cn.itcast.scala.chapter10

/*
在创建类的对象时,为该对象指定混入某个trait,且只有混入了trait的对象才具有trait中的方法,
而其他该类的对象则没有; 在创建对象时,使用 with 关键字指定混入某个 trait;
*/

trait LoggedTrait {

def log (msg : String)={}
}

trait MyLogger extends LoggedTrait{

override def log(msg : String) = println("log : "+msg)
}

class PersonForMixTraitMethod(val name : String) extends LoggedTrait{

def sayHello ={
  println("Hi , I ' m "+ this.name)
  log("sayHello method is invoked")
}
}

object PersonForMixTraitMethod{
  def main(args: Array[String]): Unit = {
    val tom = new PersonForMixTraitMethod("Tom").sayHello

    val rose = new PersonForMixTraitMethod("Rose") with MyLogger
    rose.sayHello
  }
}