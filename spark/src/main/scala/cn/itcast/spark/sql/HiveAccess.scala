package cn.itcast.spark.sql


import org.apache.spark.sql.{SaveMode, SparkSession, types}
import org.apache.spark.sql.types._

object HiveAccess {
  def main(args: Array[String]): Unit = {

    // 1.创建SparkjSession
    val spark = SparkSession.builder()
      .appName("HiveAccess")
      .enableHiveSupport()    // 开启hive支持
      .config("hive.metastore.uris" , "thrift://node01:9083")   // 指定metastore的位置
      .config("spark.sql.warehouse.dir" , "/dataset/hive")    // 指定warehouse的位置
      .getOrCreate()

    /*
    2.读取数据
    因为要在集群中执行, 为保证每个机器都可以读取文件所以上传到HDFS上,所有机器都可以访问到HDFS上的
    文件, 它是一个外部系统
    *///2.1上传文件到HDFS
    // 2.2 使用 DF 读取数据
    val schema = StructType(
      List(
        StructField("name" , StringType),
        StructField("age" , IntegerType),
        StructField("gpa" , FloatType)
      )
    )

    val dataFrame01 = spark.read
      .option("delimiter" , "\t")
        .schema(schema)   // 传入结构信息
      .csv("hdfs://node01:8020/dataset/studenttab10k")

    import spark.implicits._

    val resultDF = dataFrame01.where('age > 50)

    // 3.写入数据, 使用写入表的API
    resultDF.write
      .mode("overwrite")
      .saveAsTable("spark03.student")

  }
}
