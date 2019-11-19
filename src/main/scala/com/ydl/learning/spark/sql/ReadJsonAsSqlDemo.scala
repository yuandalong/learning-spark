package com.ydl.learning.spark.sql

import com.ydl.utils.SparkUtils

/**
  * Demo
  *
  * Created by ydl on 2018/1/10.
  */
class ReadJsonAsSqlDemo {

}

object ReadJsonAsSqlDemo {

  //将sql转成样式类时，样式类必须定义为全局的，否则会编译失败
  // Note: Case classes in Scala 2.10 can support only up to 22 fields. To work around this limit,
  // you can use custom classes that implement the Product interface
  case class Person(name: String, age: Long)

  //注意path的值，如果项目里加了core-site或者hdfs-site等相关配置，写相对路径的话默认会走hdfs
  //如果要走本地文件，要在path里写绝对路径且用file开头
  //而且如果程序中读取的是本地文件，那么，要在所有的节点都有这个数据文件，只在master中有这个数据文件时执行程序时一直报找不到文件
  private val path = "file:/Volumes/work/code/study/learning-spark/src/main/resources/people_json.txt"

  def main(args: Array[String]): Unit = {
    val sparkSession = SparkUtils.getSession("Spark SQL basic example", "local[2]")
    // For implicit conversions like converting RDDs to DataFrames
    import sparkSession.implicits._
    //val df = sparkSession.read.json(path)
    //和上面一行等效，format支持的格式包括json, parquet, jdbc, orc, libsvm, csv, text
    val df = sparkSession.read.format("json").load(path)
    //数据存储为hdfs格式的文件
    //df.write.format("parquet").save("src/main/resources/people_parquet")
    //数据分桶
    //df.write.bucketBy(10,"colname")
    //top 20 rows
    df.show()
    //打印表结构
    df.printSchema()
    df.select("name").show()
    //$用到了spark.implicits
    df.select($"name", $"age" + 1).show()
    //过滤
    df.filter($"age" > 21).show()
    //分组
    df.groupBy("age").count().show()

    //注册一个表名为people的临时表，临时表只在本sqlSession生命周期内有效
    df.createOrReplaceTempView("people")
    val sqlDF = sparkSession.sql("select * from people")
    sqlDF.show()


    // 注册全局表
    df.createGlobalTempView("people")
    // 全局表查询时表名加`global_temp`前缀
    sparkSession.sql("SELECT * FROM global_temp.people").show()
    // 全局表跨越sqlsession，在整个spark app内有效
    sparkSession.newSession().sql("SELECT * FROM global_temp.people").show()


    //使用对象创建DateFrames
    val caseClassDs = Seq(Person("Andy", 32)).toDF()
    caseClassDs.show()
    val primitiveDS = Seq(1, 2, 3).toDS()
    primitiveDS.show()

    //转换成对象
    val peopleDS = df.as[Person]
    peopleDS.show()
  }
}
