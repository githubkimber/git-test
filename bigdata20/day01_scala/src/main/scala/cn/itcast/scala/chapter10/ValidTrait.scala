package cn.itcast.scala.chapter10

trait ValidTrait {
def getName : String
def valid : Boolean = {"Tom".equals(this.getName)}
}

class PersonForValid(val name : String) extends ValidTrait{
 def getName: String = this.name
}

object PersonForValid{
  def main(args: Array[String]): Unit = {

    val person = new PersonForValid("Rose")
    println(person.valid)
  }
}