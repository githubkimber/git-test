package cn.itcast.taxi

import org.json4s.jackson.Serialization

object JsonTest {
  def main(args: Array[String]): Unit = {
    import org.json4s._
    import org.json4s.jackson.JsonMethods._
    import org.json4s.jackson.Serialization.{read , write}

    val product =
      """
        |{"name":"Toy" , "price":35.35}
      """.stripMargin

    println( parse(product) \ "name" )  // 反斜杠 /  方法表示: 查找前面结果中的"name"值 (用于查找)

    // 隐式转换的形式提供格式工具, 例如 如何解析时间字符串
    implicit val formats = Serialization.formats(NoTypeHints)

    // 将JSON 字符串数据解析为对象
    val productObj1 = parse(product).extract[Product]     // extract :转换类型
    println(productObj1)

    // 可以通过 read 方法,直接将JSON字符创数据转换为对象, 但是这种方式就无法进行搜索了
    val productObj2 = read[Product](product)
    println(productObj2)

    // 可以将对象转为JSON字符串
    val productObj3 = Product("电视" , 10.50)
    val jsonStr = write(productObj3)
    println(jsonStr)
  }


}

case class Product(name : String , price : Double)