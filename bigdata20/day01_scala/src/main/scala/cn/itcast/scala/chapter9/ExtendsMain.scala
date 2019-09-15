package cn.itcast.scala.chapter9

object ExtendsMain {

def main(args: Array[String]): Unit = {

val student = new Student1

    val score = student.getScore
  println("score: "+score)

  val name = student.getName
  println("name: "+name)
  }
}
