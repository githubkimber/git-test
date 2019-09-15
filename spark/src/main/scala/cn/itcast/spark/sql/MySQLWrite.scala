package cn.itcast.spark.sql

import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.sql.types._

/*
MySql 的访问方式有两种: 使用本地运行, 提交到集群运行,
写入 MySql 数据时 ,使用本地运行, 读取的时候使用集群运行
*/
object MySQLWrite {

  def main(args: Array[String]): Unit = {

    // 1.创建SparkSession 对象
    val spark = SparkSession.builder()
        .master("local[6]")
        .appName("my_sql")
        .getOrCreate()

    // 2.读取数据创建 dataFrame
    val schema = StructType(
      List(
        StructField("name" , StringType),
        StructField("age" , IntegerType),
        StructField("gpa" , FloatType)
      )
    )

    val df = spark.read
      .schema(schema)
      .option("delimiter" , "\t")
      .csv("dataset/studenttab10k")

    // 3.处理数据
    val resultDF = df.where("age < 30")

    // 4.连接mysql
    resultDF.write
      .mode("overwrite")
      .format("jdbc")
      .option("url" , "jdbc:mysql://node03:3306/spark02")
      .option("dbtable" , "student")
      .option("user" , "shadiao")
      .option("password" , "123456")
      .save()
  }


}
