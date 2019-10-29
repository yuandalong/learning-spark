package com.ydl.learning.spark.hive

import org.apache.spark.sql.{Row, SaveMode, SparkSession}
import org.apache.spark.sql.types.{IntegerType, LongType, StructField, StructType}

/**
  * spark操作hive demo
  *
  * @author ydl
  * @since 2019-10-29
  */

object HiveDemo {

  //注意使用toDS时，case class必须定义在调用方法外面，否则会报value toDS is not a member of Seq
  case class Test(id: Int, num: Int)

  private val spark: SparkSession = SparkSession.builder()
    .appName("hive_demo")
    .master("local[2]")
    .config("spark.sql.warehouse.dir", "hdfs://home/user/hive/warehouse")
    .config("hive.exec.dynamic.partition", "true")
    .config("hive.exec.dynamic.partition.mode", "nonstrict")
    .config("hive.exec.max.dynamic.partitions", 2000)
    .enableHiveSupport()
    .getOrCreate()

  /**
    * 数据查询
    */
  def select(): Unit = {
    spark.sql("show databases").show()
    spark.sql("select count(*) from ydl.test").show()
    //    spark.sql("insert into ydl.test values(2,'test')")
    //    spark.sql("select count(*) from ydl.test").show()
    val dataFrame = spark.sql("show databases")
    println()
    //读数据
    dataFrame.collect().foreach(row => {
      //根据字段名获取数据
      println(row.getAs("databaseName"))
      //根据索引值获取数据
      println(row.getLong(0))
    })
  }


  /**
    * rdd里的数据写入hive，如读文件等
    */
  def fromRdd(): Unit = {
    //设置数据
    val rdd = spark.sparkContext.parallelize(Array("3 26","4 27")).map(_.split(" "))
    //设置模式信息
    val schema = StructType(List(StructField("id", IntegerType, true), StructField("num", LongType, true)))
    //创建Row对象，每个Row对象都是rowRDD中的一行
    val rowRDD = rdd.map(p => Row(p(0).toInt, p(1).toLong))
    //建立起Row对象和模式之间的对应关系，也就是把数据和模式对应起来
    val df = spark.createDataFrame(rowRDD, schema)
    //查看df
    df.show()
    //注册临时表
    df.createOrReplaceTempView("temp")
    //写hive
    spark.sql("insert into ydl.test select * from temp")
  }

  /**
    * list等数据结构里的数据写入hive
    */
  def fromList(): Unit = {
    val test1 = Test(112, 2)
    val test2 = Test(312, 4)
    import spark.implicits._
    val ds = Seq(test1, test2).toDS()
    val df = ds.toDF()
    //打印模式信息
    df.printSchema()
    //直接写到hive里,Append的话最好是原来不存在的表，通过spark直接创建，否则有可能会表结构不一致导致写入失败，Overwrite无所谓
    df.write.mode(SaveMode.Overwrite).format("hive").saveAsTable("ydl.test")
    //先创建临时表，然后把临时表里的数据写入hive表，此种情况适用于写数据到hive分区表
    //    df.createOrReplaceTempView("sparktemp")
    //    spark.sql("insert overwrite TABLE partition_table  PARTITION(partition_date = "123") " +
    //          "SELECT * from sparktemp")
  }

  def main(args: Array[String]): Unit = {
//    fromList()
    fromRdd()
  }
}