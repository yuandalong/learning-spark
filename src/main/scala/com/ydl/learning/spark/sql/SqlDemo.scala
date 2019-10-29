package com.ydl.learning.spark.sql

import org.apache.spark.sql.Row

/**
  * spark sql demo
  * http://spark.apache.org/docs/2.2.3/sql-programming-guide.html
  *
  * @author ydl
  * @since 2019-10-18
  */
object SqlDemo extends App {

  import org.apache.spark.sql.SparkSession

  val spark = SparkSession
    .builder()
    .appName("Spark SQL basic example")
    .master("local[2]")
    .config("spark.some.config.option", "some-value")
    .getOrCreate()

  // For implicit conversions like converting RDDs to DataFrames
  import spark.implicits._

  val dataFilePath = "/Users/ydl/work/soft/spark-2.2.3-bin-hadoop2.7/"
  //  读json数据
  val df = spark.read.json(dataFilePath + "examples/src/main/resources/people.json")

  // Displays the content of the DataFrame to stdout
  df.show()
  // +----+-------+
  // | age|   name|
  // +----+-------+
  // |null|Michael|
  // |  30|   Andy|
  // |  19| Justin|
  // +----+-------+

  // Print the schema in a tree format
  df.printSchema()
  // root
  // |-- age: long (nullable = true)
  // |-- name: string (nullable = true)

  // Select only the "name" column
  df.select("name").show()
  // +-------+
  // |   name|
  // +-------+
  // |Michael|
  // |   Andy|
  // | Justin|
  // +-------+

  // Select everybody, but increment the age by 1
  df.select($"name", $"age" + 1).show()
  // +-------+---------+
  // |   name|(age + 1)|
  // +-------+---------+
  // |Michael|     null|
  // |   Andy|       31|
  // | Justin|       20|
  // +-------+---------+

  // Select people older than 21
  df.filter($"age" > 21).show()
  // +---+----+
  // |age|name|
  // +---+----+
  // | 30|Andy|
  // +---+----+

  // Count people by age
  df.groupBy("age").count().show()
  // +----+-----+
  // | age|count|
  // +----+-----+
  // |  19|    1|
  // |null|    1|
  // |  30|    1|
  // +----+-----+

  //  注册临时表，只能在本session里查到
  // Register the DataFrame as a SQL temporary view
  df.createOrReplaceTempView("people")

  val sqlDF = spark.sql("SELECT * FROM people")
  sqlDF.show()
  // +----+-------+
  // | age|   name|
  // +----+-------+
  // |null|Michael|
  // |  30|   Andy|
  // |  19| Justin|
  // +----+-------+

  //  注册全局临时表，可以其他session里查到
  // Register the DataFrame as a global temporary view
  df.createGlobalTempView("people")

  // Global temporary view is tied to a system preserved database `global_temp`
  spark.sql("SELECT * FROM global_temp.people").show()
  // +----+-------+
  // | age|   name|
  // +----+-------+
  // |null|Michael|
  // |  30|   Andy|
  // |  19| Justin|
  // +----+-------+

  // Global temporary view is cross-session
  spark.newSession().sql("SELECT * FROM global_temp.people").show()

  // +----+-------+
  // | age|   name|
  // +----+-------+
  // |null|Michael|
  // |  30|   Andy|
  // |  19| Justin|
  // +----+-------+

  // Note: Case classes in Scala 2.10 can support only up to 22 fields. To work around this limit,
  // you can use custom classes that implement the Product interface
  case class Person(name: String, age: Long)

  // Encoders are created for case classes
  val caseClassDS = Seq(Person("Andy", 32)).toDS()
  caseClassDS.show()
  // +----+---+
  // |name|age|
  // +----+---+
  // |Andy| 32|
  // +----+---+

  // Encoders for most common types are automatically provided by importing spark.implicits._
  val primitiveDS = Seq(1, 2, 3).toDS()
  primitiveDS.map(_ + 1).collect() // Returns: Array(2, 3, 4)

  // DataFrames can be converted to a Dataset by providing a class. Mapping will be done by name
  val path = dataFilePath + "examples/src/main/resources/people.json"
  val peopleDS = spark.read.json(path).as[Person]
  peopleDS.show()
  // +----+-------+
  // | age|   name|
  // +----+-------+
  // |null|Michael|
  // |  30|   Andy|
  // |  19| Justin|
  // +----+-------+

  // Create an RDD of Person objects from a text file, convert it to a Dataframe
  val peopleDF = spark.sparkContext
    .textFile(dataFilePath + "examples/src/main/resources/people.txt")
    .map(_.split(","))
    .map(attributes => Person(attributes(0), attributes(1).trim.toInt))
    .toDF()
  // Register the DataFrame as a temporary view
  peopleDF.createOrReplaceTempView("people")

  // SQL statements can be run by using the sql methods provided by Spark
  val teenagersDF = spark.sql("SELECT name, age FROM people WHERE age BETWEEN 13 AND 19")

  // The columns of a row in the result can be accessed by field index
  teenagersDF.map(teenager => "Name: " + teenager(0)).show()
  // +------------+
  // |       value|
  // +------------+
  // |Name: Justin|
  // +------------+

  // or by field name
  teenagersDF.map(teenager => "Name: " + teenager.getAs[String]("name")).show()
  // +------------+
  // |       value|
  // +------------+
  // |Name: Justin|
  // +------------+

  // No pre-defined encoders for Dataset[Map[K,V]], define explicitly
  implicit val mapEncoder = org.apache.spark.sql.Encoders.kryo[Map[String, Any]]
  // Primitive types and case classes can be also defined as
  // implicit val stringIntMapEncoder: Encoder[Map[String, Any]] = ExpressionEncoder()

  // row.getValuesMap[T] retrieves multiple columns at once into a Map[String, T]
  teenagersDF.map(teenager => teenager.getValuesMap[Any](List("name", "age"))).collect()
  // Array(Map("name" -> "Justin", "age" -> 19))




  import org.apache.spark.sql.types._

  // Create an RDD
  val peopleRDD = spark.sparkContext.textFile("examples/src/main/resources/people.txt")

  // The schema is encoded in a string
  val schemaString = "name age"

  // Generate the schema based on the string of schema
  val fields = schemaString.split(" ")
    .map(fieldName => StructField(fieldName, StringType, nullable = true))
  val schema = StructType(fields)

  // Convert records of the RDD (people) to Rows
  val rowRDD = peopleRDD
    .map(_.split(","))
    .map(attributes => Row(attributes(0), attributes(1).trim))

  // Apply the schema to the RDD
  val peopleDF2 = spark.createDataFrame(rowRDD, schema)

  // Creates a temporary view using the DataFrame
  peopleDF2.createOrReplaceTempView("people")

  // SQL can be run over a temporary view created using DataFrames
  val results = spark.sql("SELECT name FROM people")

  // The results of SQL queries are DataFrames and support all the normal RDD operations
  // The columns of a row in the result can be accessed by field index or by field name
  results.map(attributes => "Name: " + attributes(0)).show()
  // +-------------+
  // |        value|
  // +-------------+
  // |Name: Michael|
  // |   Name: Andy|
  // | Name: Justin|
  // +-------------+


}
