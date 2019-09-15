package cn.itcast.spark.sql

import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}
import org.junit.Test

class Column {

  // 1.创建SparkSession对象
  val spark = SparkSession.builder()
    .appName("column")
    .master("local[6]")
    .getOrCreate()

  // 2.导入隐式转换
  import spark.implicits._

  @Test
  def creation() : Unit = {

    val ds: Dataset[Person] = Seq(Person("张三" , 10) , Person("李四" , 15)).toDS()
    val ds1: Dataset[Person] = Seq(Person("张三" , 10) , Person("李四" , 15)).toDS()
    val df: DataFrame = Seq(Person("张三" , 10) , Person("李四" , 15)).toDF()

    // ' 必须导入spark的隐式转换才能用 str.intern()       通过 ' 会生成一个 Symbol 对象
    val column = 'name

    // $ 必须导入spark的隐式转换才能用      通过 $ 可以生成一个 Column 对象
    val column1 = $"name"

    // col 必须导入 functions才能用      创建 Column 对象
    import org.apache.spark.sql.functions._

    val column2 = col("name")

    // column 必须导入 functions才能用      创建 Column 对象
    val column3 = column("name")

    ds.select(column).show()
    df.select(column).show()

    df.where(column === "张三").show()

    // dataset.col
    val column4 = ds.col("name")
    val column5 = ds1.col("name")

    ds.select(column4).show()

    ds.join(ds1 , ds.col("name") === ds1.col("name"))

    // dataset.apply  通过 Dataset 对象的 apply 方法来获取一个关联此 Dataset 的 Column 对象
    val column6 = ds.apply("name")
    val column7 = ds("name")
  }

@Test
def as() : Unit = {
  val ds = Seq(Person("张三" , 10) , Person("李四" , 15)).toDS()

  // sql 语言: select name , count(age) as age from table group by name
  // 給列创建别名
  ds.select('age as "oldage").show()

  // 给列转换数据类型
  ds.select('age.as[Long] ).show()
}

@Test
def api() : Unit = {
  val ds = Seq(Person("张三" , 10) , Person("张三" , 20) , Person("李四" , 15)).toDS()

  // 需求一: ds,增加列, 双倍年龄
  ds.withColumn( "doubled" , 'age * 2).show()

  // 需求二: 模糊查询
  // SQL 语言: select * from table where name like 张%;
  ds.where('name like("张%")).show()

  // 需求三: 排序,正反序
  ds.sort('age.asc).show()    // 升序

  ds.orderBy('age.desc).show()    // 降序

  // 需求四: 枚举判断  查看这三人的信息
  ds.where('name isin("张三" , "王五" , "赵六")).show()


}

}
