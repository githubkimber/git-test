package cn.itcast.taxi

object EitherTest {
  def main(args: Array[String]): Unit = {


  // 相当于 parse方法
  def process(b : Double) : Double = {
  val a = 10.0
    a / b
  }

  // Either :正确返回参数一, 否则返回参数二
  // Option :有值则为Some , 无值则为None
  def safe(f : Double => Double , b : Double ) : Either[Double , (Double , Exception)] = {
    try {
      val result = f(b)
      Left(result)
    }catch {
      case e : Exception => Right(b , e)
    }
  }

  val result = safe(process , 0)
  val boolean: Boolean = result.isLeft

  result match{
    case Left(r) => println(r)
    case Right((b , e)) => println(b , e)
  }


}

}
