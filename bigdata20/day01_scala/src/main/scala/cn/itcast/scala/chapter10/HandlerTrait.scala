package cn.itcast.scala.chapter10

/*
如果一个class类,继承了多个trait(特质),调用特质当中的方法,从右往左一直执行; trait(特质)的初始化,是从左往右一直执行;
*/

trait HandlerTrait {
def handle(data : String) = println("last one")
}

trait DataValidlerTrait extends HandlerTrait {

 override def handle(data: String): Unit = {
    println("check data : " + data)
    super.handle(data)
  }
}

trait SignatureValidHandlerTrait extends HandlerTrait{

  override def handle(data : String)= {
    println("caheck signature : "+data)
    super.handle(data)
  }
}

class PersonForRespLine(val name : String) extends SignatureValidHandlerTrait with DataValidlerTrait{

  def sayHello: Unit ={
    println("Hello , "+this.name)
    this.handle(this.name)
  }
}

object PersonForRespLine{
  def main(args: Array[String]): Unit = {

    val p = new PersonForRespLine("tom")
    p.sayHello
  }
}