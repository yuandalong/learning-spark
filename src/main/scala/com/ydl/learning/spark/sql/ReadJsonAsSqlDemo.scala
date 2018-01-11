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
    //将sql转成样式类时，样式列必须定义为全局的，否则会编译失败
    // Note: Case classes in Scala 2.10 can support only up to 22 fields. To work around this limit,
    // you can use custom classes that implement the Product interface
    case class Person(name: String, age: Long)
    private val path = "src/main/resources/people_json.txt"
    def main(args: Array[String]): Unit = {
        val sparkSession = SparkUtils.getSession("Spark SQL basic example", "local[2]")
        // For implicit conversions like converting RDDs to DataFrames
        import sparkSession.implicits._
        val df = sparkSession.read.json(path)
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
        val caseClassDs = Seq(Person("Andy",32)).toDF()
        caseClassDs.show()
        val primitiveDS = Seq(1, 2, 3).toDS()
        primitiveDS.show()

        //转换成对象
        val peopleDS = df.as[Person]
        peopleDS.show()
    }
}
