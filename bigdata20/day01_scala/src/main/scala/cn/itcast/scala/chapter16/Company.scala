package cn.itcast.scala.chapter16

// 案例5>.员工领薪水

object Company {
implicit val aaa = "张三"
implicit val bbb = 10000.00
}

class Boss{
  def callName() (implicit name : String) : String = {
    name + " is coming !"
  }

  def getMoney()(implicit money : Double) : String = {
    " 当月薪水 "+money
  }
}

object Boss extends App{
  import Company._
  val boss = new Boss
  // 不用传参自动匹配隐式参数,对应的隐式参数只能匹配定义一个, 定义多了会报错
  println(boss.callName()+boss.getMoney())
}