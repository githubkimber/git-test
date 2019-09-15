package cn.itcast.scala.chapter15

import scala.util.matching.Regex

object split1 {
  def main(args: Array[String]): Unit = {

    val pattern1 = new Regex("(S|s)cala")
    val pattern2 = "(S|s)cala".r
    val str = "Scala is scalable and cool"
    println((pattern2 findAllIn str).mkString(","))
  }
}
