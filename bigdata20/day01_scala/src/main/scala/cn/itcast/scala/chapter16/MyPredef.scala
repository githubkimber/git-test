package cn.itcast.scala.chapter16



import java.io.File

import scala.io.Source





// 案例2>.让File类具备RichFile类中的read方法


object MyPredef {
  implicit def FileRead2(file : File) = new RichFile(file)
}

class RichFile(f : File){
def readFile1 = Source.fromFile(f , "GBK").mkString
}

object RichFile{

  import MyPredef.FileRead2

  def main(args: Array[String]): Unit = {
    val file1 = new File("F:\\BaiduNetdiskDownload\\黑马课堂\\课堂资料\\就业班\\Spark内存计算\\scala基础\\day03\\files\\file.txt")
    println(file1.readFile1)
  }
}
