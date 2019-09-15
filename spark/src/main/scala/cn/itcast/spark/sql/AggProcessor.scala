package cn.itcast.spark.sql

import org.apache.spark.sql.{DataFrame, RelationalGroupedDataset, SparkSession}
import org.apache.spark.sql.types._
import org.junit.Test

class AggProcessor {

  // 1.创建 Sparkssesion
  val spark = SparkSession.builder()
    .appName("goupby")
    .master("local[6]")
    .getOrCreate()

  // 导入隐式转换
  import spark.implicits._

@Test
def groupBy() : Unit = {

  // 2.数据读取
  val schema = StructType(
    List(
      StructField("id" , IntegerType),
      StructField("year" , IntegerType),
      StructField("month" , IntegerType),
      StructField("day" , IntegerType),
      StructField("hour" , IntegerType),
      StructField("season" , IntegerType),
      StructField("pm" , DoubleType)
    )
  )

  val df = spark.read
    .option("header" , true)
    .schema(schema)
    .csv("dataset/Beijing_pm5/beijingpm_with_nan.csv")

  // 3.数据去掉空值
  val cleandf = df.where('pm =!= Double.NaN)
    cleandf.show()


 // import scala.collection.JavaConverters._




  // 4.使用functions 函数来完成聚合 agg()
  // groupBy 分组
  val groupdf: RelationalGroupedDataset = cleandf.groupBy('year , 'month)

  import org.apache.spark.sql.functions._

  // select
  groupdf.agg(avg('pm) as "pm_avg")   // 先起别名再求排序
    .orderBy('pm_avg desc)            // 降序
    .show()

  groupdf.agg(avg('pm))
    .select($"avg(pm)" as "pm_avg")
    .orderBy('pm_avg)               // 默认为降序
    .show()

}

@Test
def multiAgg() : Unit = {
  val schemaFinal = StructType(
    List(
      StructField("source", StringType),
      StructField("year", IntegerType),
      StructField("month", IntegerType),
      StructField("day", IntegerType),
      StructField("hour", IntegerType),
      StructField("season", IntegerType),
      StructField("pm", DoubleType)
    )
)

val pmFinal = spark.read
  .schema(schemaFinal)
  .option("header", value = true)
  .csv("dataset/pm_final.csv")

pmFinal.show()

  import org.apache.spark.sql.functions._
// 需求1: 不同年, 不同来源, pm的值平均数
// select source , year , avg(pm) , as pm from group by source , year
val postAndyeardf = pmFinal.groupBy( 'source , 'year)
    .agg(avg('pm) as "pm")                // 先聚合再求平均数

// 需求2: 在整个数据集中,按照不同来源的来统计pm值的平均数
val postdf =   pmFinal.groupBy('source)
    .agg(avg('pm) as "pm")
    .select('source , lit(null) as "year" , 'pm)    // 造出一个空列取名为year; 这样就可以为下面两个及和合并做准备了

// 需求3: 合并在一个结果集中   union() 求两个集合的合集
postAndyeardf.union(postdf)
.sort('source , 'year.asc_nulls_last , 'pm)        // sort()同orderby也是排序 ; asc_nulls_last: 升序排列,null放在最后面;
.show()
}

@Test
def rollup() : Unit = {
  import org.apache.spark.sql.functions._

  val sales = Seq(
    ("Beijing" , 2016 , 100),
    ("Beijing" , 2017 , 200),
    ("Shanghai" , 2015 , 50),
    ("Shaihai" , 2016 , 150),
    ("Guangzhou" , 2017 , 50)
  ).toDF("city" , "year" , "amount")       // 给数据指定列

  sales.rollup('city , 'year)
    .agg(sum('amount) as "amount")
    .sort('city.asc_nulls_last , 'year.asc_nulls_last)
    .show()

  sales.groupBy('city , 'year)
    .agg(sum('amount) as "amount")
    .sort('city.asc_nulls_last , 'year.asc_nulls_last)
    .show()
}

@Test
def rollup1() : Unit = {
val schemaFinal = StructType(
      List(
        StructField("source", StringType),
        StructField("year", IntegerType),
        StructField("month", IntegerType),
        StructField("day", IntegerType),
        StructField("hour", IntegerType),
        StructField("season", IntegerType),
        StructField("pm", DoubleType)
      )
)

  val pmFinal = spark.read
    .schema(schemaFinal)
    .option("header" , value = true)
    .csv("dataset/pm_final.csv")

  // 2.聚合和统计
  // 需求1>. 每个pm值计量者, 每年pm值统计的平均数 groupBy source year
  // 需求2>. 每个pm值计量者,整体上的pm平均值 groupBy source
  // 需求3>. 全局所有的计量者, 和日期的pm值的平均值groupBy null
  import org.apache.spark.sql.functions._

  pmFinal.rollup('source , 'year)
    .agg(avg('pm) as "pm")
    .sort('source.asc_nulls_last , 'year.asc_nulls_last)
    .show()



}

@Test
def cube() : Unit = {
  val schemaFinal = StructType(
    List(
      StructField("source" , StringType),
      StructField("year" , IntegerType),
      StructField("month" , IntegerType),
      StructField("day" , IntegerType),
      StructField("hour" , IntegerType),
      StructField("season" , IntegerType),
      StructField("pm" , DoubleType)
    )
  )

  val pmFinal = spark.read
    .option("header" , true)
    .csv("dataset/pm_final.csv")

  import org.apache.spark.sql.functions._

  pmFinal.cube('source , 'year)                   // rollup() 多了year平均数,
    .agg(avg('pm) as "pm")
    .sort('source.asc_nulls_last , 'year.asc_nulls_last)
    .show()

}

@Test
def cubeSql() : Unit = {
  val schemaFinal = StructType(
    List(
      StructField("source" , StringType),
      StructField("year" , IntegerType),
      StructField("month" , IntegerType),
      StructField("day" , IntegerType),
      StructField("hour" , IntegerType),
      StructField("season" , IntegerType),
      StructField("pm" , DoubleType)
    )
  )

  val pmFinal = spark.read
    .option("header" , true)
    .csv("dataset/pm_final.csv")

  pmFinal.createOrReplaceTempView("pm_final")   // 创建临时表

  val result = spark.sql(
    "select source , year, avg(pm) as pm from pm_final " +
    "group by source , year" +
    "grouping sets((source , year) , source , year , ( )) " +
    "order by source asc nulls last , year asc nulls last"
  )

  result.show()
}



}