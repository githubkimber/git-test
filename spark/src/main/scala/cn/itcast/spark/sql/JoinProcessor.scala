package cn.itcast.spark.sql

import org.apache.spark.sql.SparkSession
import org.junit.Test

class JoinProcessor {
  val spark = SparkSession.builder()
    .appName("join")
    .master("local[6]")
    .getOrCreate()

import spark.implicits._

  private val person = Seq((0 , "lucy" , 0) , (1 , "lily" , 0) , (2 , "Tim" , 2) , (3 , "Danial" , 3)).toDF("id" , "name" , "cityId")
  private val cities = Seq((0 , "Beijing") , (1 , "shanghai") , (2 , "Guangzhou")).toDF("id" , "name")

  person.createOrReplaceTempView("person01")
  cities.createOrReplaceTempView("cities01")

@Test
def introToin() : Unit = {

  val df = person.join(cities , person.col("cityId") === cities.col("id"))
    .select(person.col("id") , person.col("name") , cities.col("name") as "city")
    df.show()

  df.createOrReplaceTempView("user_city")

  spark.sql("select id , name , city from user_city where city = 'Beijing'")
    .show()
}

  @Test
  def crossJoin() : Unit = {
    person.crossJoin(cities)
      .where(person.col("cityId") === cities.col("id"))
      .show()

    spark.sql("select u.id , u.name , c.name from person01 u cross join cities01 c " +       // user u  和 cities c 分别为临时别名,
      "where u.cityId = c.id")
      .show()
  }

@Test
  def inner() : Unit = {
  person.join(cities,
    person.col("cityId") === cities.col("id"),
    joinType = "inner")
    .show()

  spark.sql("select p.id , p.name , c.name from person01 p inner join cities01 c " +
    "on p.cityId = c.id")
    .show()
}

@Test
def fullOuter() : Unit = {

  // 内连接, 就是只显示能连接上的数据, 外连接包含一部分没有连接上的数据, 全外连接, 指左右两边没有连接
  person.join(cities,
    person.col("cityId") === cities.col("id"),
    joinType = "fullouter")
    .show()

  spark.sql("select p.id , p.name , c. name from person01 p full outer join cities01 c on p.cityId = c.id")
    .show()
}
@Test
def leftRight() : Unit = {
  // 左外连接
  person.join(cities,
    cities.col("id") === person.col("cityId"),
    joinType = "leftouter")
    .show()

  spark.sql("select p.id , p.name , c.name from person01 p left outer join cities01 c on p.cityId = c.id")
    .show()

  // 右外连接
  person.join(cities,
    person.col("cityId") === cities.col("id"),
    joinType = "rightouter")
      .show()

  spark.sql("select p.id , p.name , c.name from person01 p right outer join cities01 c on p.cityId = c.id")
    .show()

}

@Test
def leftantisemi() : Unit = {
  // leftanti
  person.join(cities,
  person.col("cityId") === cities.col("id"),
  joinType = "leftanti"
  ).show()

  spark.sql("select p.id , p.name from person01 p left anti join cities01 c on p.cityId = c.id")
    .show()

  //leftsemi
  person.join(cities,
  person.col("cityId") === cities.col("id"),
    joinType = "leftsemi"
  ).show()

  spark.sql("select p.id , p.name from person01 p left semi join cities01 c on p.cityId = c.id")
    .show()
}

}
