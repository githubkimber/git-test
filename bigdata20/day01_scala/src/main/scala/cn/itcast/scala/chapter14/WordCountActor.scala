package cn.itcast.scala.chapter14

import scala.actors.{Actor, Future}
import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.io.{BufferedSource, Source}

case class FilePath(fileNamePath : String)
case class ReplyWord(Map1 : Map[String , Int])

class WordCountActor extends Actor{
  override def act(): Unit = {
    // 使用loop + react的方式来实现不断的接受数据
    loop{
      react{
        case FilePath(fileNamePath) => {
          // 获取到了文件的路径之后, 读取文件的内容
          val fileContent : BufferedSource = Source.fromFile(fileNamePath)
          // 获取文件流当中所有的内容
          val fileContetStr: String = fileContent.mkString
          println(fileContetStr)

          // 文件内容先按照换行符切分 (windows: \r\n ; linux: \r ; mac: \n)
          val a1: Array[String] = fileContetStr.split("\r\n")
          // 将Array当中每一个元素取出来,按照空格切分
          // 外面的Array数组装的是被拆分后的单个元素, 里面的Array是一行一行的元素 ( Array[Array[String]] : 类型 ,数组元素还是由数组组成)
          val a2 : Array[Array[String]]= a1.map(x => x.split(" "))
          // a2 : Array[Array[String]] = Array{Array{hello ,world ,hadoop ,hive} ,Array{sqoop ,hadoop ,hello ,world}}
          println(a2.toBuffer)
          // flatten 打散 分解 拆分
          val a3 : Array[String] = a2.flatten
          println(a3.toBuffer)

          // flatten和map方法的合并省略了a2 , a3
          val a4 : Array[String] = a1.flatMap(x => x.split(" "))
          println(a4.toBuffer)

          // 如何将Array当中的每一个元素取出来, 变成这种形式: Array((hello, 1), (world, 1), (hadoop, 1), (hive, 1), (sqoop, 1)...)
          val a5 : Array[(String, Int)] = a4.map(x => (x , 1))
          println(a5.toBuffer)
          // 将Array当中的每一个元组取出来,作用在groupBy(分组)的函数上面
          val a6 : Map[String , Array[(String , Int)]] = a5.groupBy(x => x._1)    // x._1 获取数组中索引下标为1的元素;
          for((x , y) <- a6){
            println(x)
            println(y.toBuffer)
          }
          // 获取map当中每个value的长度, 就是我们单词出现的次数
          // 将map当中每个value取出来,作用在下面这个函数当中, 然后返回一个新的map
          val a7 : Map[String , Int] = a6.mapValues(x => x.length)
          for ((x , y) <- a7){
            println(x , y)
          }

          // 将我们得到的每一个文件的结果, 返回回去
          sender ! ReplyWord(a7)
        }
      }
    }
  }
}

object WordCountActor {
  def main(args: Array[String]): Unit = {

    val filePath = "F:\\BaiduNetdiskDownload\\黑马课堂\\课堂资料\\就业班\\Spark内存计算\\scala基础\\day03\\WordCount\\1.txt"

    val files = Array("F:\\BaiduNetdiskDownload\\黑马课堂\\课堂资料\\就业班\\Spark内存计算\\scala基础\\day03\\WordCount\\1.txt" , "F:\\BaiduNetdiskDownload\\黑马课堂\\课堂资料\\就业班\\Spark内存计算\\scala基础\\day03\\WordCount\\2.txt" , "F:\\BaiduNetdiskDownload\\黑马课堂\\课堂资料\\就业班\\Spark内存计算\\scala基础\\day03\\WordCount\\3.txt")
    val mySet = new mutable.HashSet[Future[Any]]()    // mutable : 可变长的
    val a8 = new ListBuffer[ReplyWord]

    for(f <- files) {

      val actor = new WordCountActor
      // 调用start, 准备接收消息
      actor.start()
      // 将文件的路径发送给我们的actor, 方便我们actor进行下一步的单词出现次数的统计
      // 获取到我们异步发送消息的返回值
      val value : Future[Any] = actor !! FilePath(filePath)
      mySet.add(value)
    }

    while(mySet.size > 0){
    // 过滤掉我们Set集合当中没有值的可能性
    val filter1 : mutable.HashSet[Future[Any]] = mySet.filter(x => x.isSet)
    for (eachFuture <- mySet) {
      // 注意一定要过滤掉我们set集合当中没有数据的可能性
      // 过滤掉无效数据, 就得到真正想要的数据
      val apply1 : Any = eachFuture.apply()

      def world: ReplyWord = apply1.asInstanceOf[ReplyWord]
      // 将我们真正的值转换成了样例类之后, 再加入到一个listBuffer里面去
      a8 += world
      // 加我们加入ListBuffer当中的数据全部给移除, 避免while死循环
      mySet.remove(eachFuture)
    }
    }
    val map2 : ListBuffer[Map[String , Int]] = a8.map(x => x.Map1)
    val a9 : ListBuffer[(String , Int)] = a8.map(x => x.Map1).flatten
    println("a9: "+a9)
    println("==============================================================")

    // 按照单词出现的key进行分组
    val a10 : Map[String , ListBuffer[(String , Int)]] = a9.groupBy(x => x._1)
    for((x , y) <- a10){
      println(x)
      println(y)
    }
    // 每一个value的值都是ListBuffer, 将ListBuffer里面的每一个元组的value取出来进行累加
    // foldLeft()  方法: 给一个初始值从左边开始叠加计算.
    // 推演过程:  0 + (x , y(hello , 2) --> 0 + y._2 = x --> x =2 , + y._2(hello , 2)
    // 推演过程: a10.mapValues(x => x.foldleft(0)((x ,y) => x + y._2))   精简如下:
    // 推演过程: a10.mapValues(x => x.foldLeft(0)((x , y) => x + y._2))  精简如下:
    // 0是我们的初始值, x是我们每次累加之后存放的变量, 用是我们集合当中每一个元组
    // 第一次: x1 = 0 + 元组._2   第二次: x2 = (x1 + 元组._2)       精简如下:
    val values1 : Map[String , Int] = a10.mapValues(x => x.foldLeft(0)(_ + _._2))
      println("==============================================================")
    for ((x , y) <- values1){
      println(x , y)
    }
  }
}