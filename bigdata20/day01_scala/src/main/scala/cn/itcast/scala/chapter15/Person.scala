package cn.itcast.scala.chapter15

import java.io.{FileInputStream, FileOutputStream, ObjectInputStream, ObjectOutputStream}

@SerialVersionUID(1L)
class Person extends Serializable {
  override def toString = name + "," + age

    val name = "Nick"
    val age = 20

}

object PersonMain extends App{
 override def main(args: Array[String]): Unit = {

    val nick1 = new Person
    val out1 = new ObjectOutputStream(new FileOutputStream("Nick.obj"))
    out1.writeObject(nick1)
    out1.close()

   val in1 = new ObjectInputStream(new FileInputStream("Nick.obj"))
   val saveNick1 = in1.readObject()
   in1.close()
   println(saveNick1)

  }
}