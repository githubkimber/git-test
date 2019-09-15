package cn.itcast.scala.chapter15

import java.io.PrintWriter

import scala.io.{BufferedSource, Source, StdIn}



object FileOperate {
  def main(args: Array[String]): Unit = {

    // 案例1> 使用Scala读取文件当中每一行的数据
    val file1 : BufferedSource = Source.fromFile("F:\\BaiduNetdiskDownload\\黑马课堂\\课堂资料\\就业班\\Spark内存计算\\scala基础\\day03\\files\\file.txt","GBK")
    // 获取文件的所有行
    val lines1 : Iterator[String] = file1.getLines()
    while(lines1.hasNext){
      println(lines1.next())
    }
    file1.close()

    println("=============================================")

    // 案例2> 读取文件按照一定的规则进行切分
      val file2 : BufferedSource = Source.fromFile("F:\\BaiduNetdiskDownload\\黑马课堂\\课堂资料\\就业班\\Spark内存计算\\scala基础\\day03\\files\\file2.txt","GBK")
      val split1 : Array[String] = file2.mkString.split(" ")
      println(split1.toBuffer)

    println("=============================================")

    // 案例3> 读取网络内容
    val baidu : BufferedSource = Source.fromURL("https://www.baidu.com/")
    val String : String = baidu.mkString
    println(String)

    println("=============================================")

    // 案例4>写入文件
    val printWriter1 = new PrintWriter("F:\\BaiduNetdiskDownload\\黑马课堂\\课堂资料\\就业班\\Spark内存计算\\scala基础\\day03\\files\\printWriter.txt","GBK")
    printWriter1.print("hhhh")      // 写入操作
    printWriter1.flush()
    printWriter1.close()

    println("=============================================")

    // 控制台交互--老API
    print("请输入内容")
    val consoleLine1 = Console.readLine()
    println("刚才输入的内容是:"+consoleLine1)

    // 控制台交互--新API
    print("请输入内容(新API)")
    val consoleLine2 = StdIn.readLine()
    println("刚才输入的内容是:."+consoleLine2)

  }
}
