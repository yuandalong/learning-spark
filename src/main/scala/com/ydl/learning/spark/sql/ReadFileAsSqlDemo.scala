package com.ydl.learning.spark.sql

import com.ydl.utils.SparkUtils
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}

/**
  * 读取文本文件
  *
  * Created by ydl on 2017/12/7.
  */
class ReadFileAsSqlDemo {

}

object ReadFileAsSqlDemo {
    private val path = "src/main/scala/com/ydl/learning/spark/sql/data.txt"

    // 创建一个表示客户的自定义样式类，注意此样式类必须是全局的，否则编译时报错
    // Unable to find encoder for type stored in a Dataset.  Primitive types (Int, String, etc) and Product
    // types (case classes) are supported by importing spark.implicits._  Support for serializing other types will be added in future releases.
    // (",")).map(p => Customer(p(0).toInt, p(1), p(2), p(3), p(4)))
    case class Customer(customer_id: Int, name: String, city: String, state: String, zip_code: String)

    def main(args: Array[String]): Unit = {
        val sparkSession = SparkUtils.getSession("wordCount", "local[2]")
        readFileDataAsObject(sparkSession)
        readFileAndSetSchema(sparkSession)
    }

    /**
      * 读文本文件并转换成对象后进行sql统计
      *
      * @param sparkSession
      */
    private def readFileDataAsObject(sparkSession: SparkSession): Unit = {
        // 导入语句，可以隐式地将RDD转化成DataFrame
        import sparkSession.implicits._
        // 用数据集文本文件创建一个Customer对象的DataFrame
        val dfCustomers = sparkSession.read.textFile(path).map(_.split(",")).map(p => Customer(p(0).toInt, p(1), p(2), p(3), p(4)))
        //也可先读取文件到RDD之后隐式转换成DateFram
        //val dfCustomers =  sparkSession.sparkContext.textFile("src/main/scala/com/ydl/learning/spark/sql/data.txt").map(_.split
        //(",")).map(p => Customer(p(0).toInt, p(1), p(2), p(3), p(4))).toDF()
        // 将DataFrame注册为一个表
        dfCustomers.createOrReplaceTempView("customers")

        // 显示DataFrame的内容
        dfCustomers.show()

        // 打印DF模式
        dfCustomers.printSchema()

        // 选择客户名称列
        dfCustomers.select("name").show()

        // 选择客户名称和城市列
        dfCustomers.select("name", "city").show()

        // 根据id选择客户
        dfCustomers.filter(dfCustomers("customer_id").equalTo(500)).show()

        // 根据邮政编码统计客户数量
        dfCustomers.groupBy("zip_code").count().show()
    }

    /**
      * 读文本，自定义字段名
      *
      * @param spark
      */
    private def readFileAndSetSchema(spark: SparkSession): Unit = {
        import spark.implicits._
        // $example on:programmatic_schema$
        // Create an RDD
        val peopleRDD = spark.sparkContext.textFile(path)

        // The schema is encoded in a string
        val schemaString = "id name city state zip_code"

        // Generate the schema based on the string of schema
        val fields = schemaString.split(" ")
                .map(fieldName => {
                    if (fieldName == "id") {
                        StructField(fieldName, IntegerType, nullable = true)
                    }
                    else {
                        StructField(fieldName, StringType, nullable = true)
                    }

                })
        val schema = StructType(fields)

        // Convert records of the RDD (people) to Rows
        val rowRDD = peopleRDD
                .map(_.split(","))
                .map(attributes => Row(attributes(0).trim.toInt, attributes(1).trim, attributes(2), attributes(3),
                    attributes
                    (4)))

        // Apply the schema to the RDD
        val peopleDF = spark.createDataFrame(rowRDD, schema)

        // Creates a temporary view using the DataFrame
        peopleDF.createOrReplaceTempView("people")

        // SQL can be run over a temporary view created using DataFrames
        val results = spark.sql("SELECT id,name FROM people where id > 200")
        results.show()
        // The results of SQL queries are DataFrames and support all the normal RDD operations
        // The columns of a row in the result can be accessed by field index or by field name
        results.map(attributes => "Name: " + attributes(1)).show()
    }
}