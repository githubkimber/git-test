package cn.itcast.spark.sql

import org.apache.spark.sql.{KeyValueGroupedDataset, SparkSession}
import org.junit.Test

class UnTypedTransformation {

  // 创建SparkSession
  val spark = SparkSession.builder()
    .master("local[6]")
    .appName("untype")
    .getOrCreate()

  // 导入隐式转换
  import spark.implicits._

  @Test
  def select() : Unit = {
  val ds = Seq(Person("李四",20) , Person("张三" , 15) ,Person("张三" , 10)).toDS()

  ds.select( 'name )
    .show()

  ds.selectExpr("sum(age)")
    .show()

  import org.apache.spark.sql.functions._

  ds.select(expr("sum(age)"))
    .show()

  }

  @Test
  def column() : Unit = {
    val ds = Seq(Person("李四", 20), Person("张三", 15), Person("张三", 10)).toDS()

    import org.apache.spark.sql.functions._

    // rand() 随机数方法;
    // ds 中添加一个列,这个列叫random,是一个随机数;
    ds.withColumn("random", expr("rand()")).show()

    // ds 中添加一个列,这个列叫name_new, 把name这个列赋给它
    ds.withColumn("name_new", 'name).show()

    // ds 中添加一个列,这个列叫name_jok, 把name这个列判断等于空字符串否再赋给它
    ds.withColumn("name_jok", 'name === "").show()


    // withColumnRenamed() 改名字; 参数一:旧名字, 参数二:新名字;
    ds.withColumnRenamed("name", "new_name")
      .show()
  }

  @Test
  def groupBy() : Unit = {
    val ds = Seq(Person("李四", 20) , Person("张三" , 15) ,Person("张三" , 10)).toDS()

    // GroupByKey是有类型的
    val rdd1: KeyValueGroupedDataset[String, Person] = ds.groupByKey(x => x.name)

import org.apache.spark.sql.functions._
    // GroupBy是无类型的 agg 是跟着groupBy的
    // mean() 求平均数.
    ds.groupBy('name).agg(mean("age")).show()
  }
}
