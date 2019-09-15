package cn.itcast.scala.chapter10

trait Logger {
  def log(message : String) : Unit = println(message)
}

class PersonForLog(val name : String) extends Logger{

  def makeFriends(other : PersonForLog) = {
    println("hello  , "+other.name+" ! My name is " + this.name + " , I miss you!")
    this.log("makeFrends method is invoked with parameter PersonForLog[name = " + other.name+"]")
  }
}

object PersonForLog{
  def main(args: Array[String]): Unit = {
    val p1 = new PersonForLog("jack")
    val p2 = new PersonForLog("rose")
    p1.makeFriends(p2)
  }
}