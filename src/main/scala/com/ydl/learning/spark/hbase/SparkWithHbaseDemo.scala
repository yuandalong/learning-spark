//package com.ydl.learning.spark.hbase
//
//import com.ydl.utils.SparkUtils
//import it.nerdammer.spark.hbase._
//import it.nerdammer.spark.hbase.conversion.{FieldReader, FieldWriter}
//import org.apache.hadoop.hbase.util.Bytes
//import org.apache.spark.SparkContext
//
///**
//  * spark使用hbase demo
//  *
//  * Created by ydl on 2018/1/15.
//  */
//class SparkWithHbaseDemo {
//
//}
//
//object SparkWithHbaseDemo {
//  def main(args: Array[String]): Unit = {
//    val confMap = Map("spark.hbase.host" -> "app-dev1-xyplus-a1")
//    val sc = SparkUtils.getContext("sparkWhihHbase", "local[2]", confMap)
//    //        write(sc)
//    //        read(sc)
//    readObject(sc)
//
//  }
//
//  /**
//    * 写数据
//    *
//    * @param sc
//    */
//  def write(sc: SparkContext): Unit = {
//    val rdd = sc.parallelize(1 to 10).map(i => (i.toString, (i + 1).toString, "Hello"))
//    rdd.foreach(println)
//    rdd.toHBaseTable("t_book").toColumns("column1", "column2").inColumnFamily("base").save()
//
//  }
//
//  /**
//    * 读数据
//    *
//    * @param sc
//    */
//  def read(sc: SparkContext): Unit = {
//    sc.hbaseTable[(String, String, String)]("t_book")
//      .select("column1", "column2")
//      .inColumnFamily("base").foreach(println)
//  }
//
//  def readObject(sc: SparkContext): Unit = {
//    case class MyData(id: Int, prg: Int, name: String)
//    implicit def myDataWriter: FieldWriter[MyData] = new FieldWriter[MyData] {
//      override def map(data: MyData): HBaseData =
//        Seq(
//          Some(Bytes.toBytes(data.id)),
//          Some(Bytes.toBytes(data.prg)),
//          Some(Bytes.toBytes(data.name))
//        )
//
//      override def columns = Seq("prg", "name")
//    }
//
//    implicit def myDataReader: FieldReader[MyData] = new FieldReader[MyData] {
//      override def map(data: HBaseData): MyData = MyData(
//        id = Bytes.toInt(data.head.get),
//        prg = Bytes.toInt(data.drop(1).head.get),
//        name = Bytes.toString(data.drop(2).head.get)
//      )
//
//      override def columns = Seq("prg", "name")
//    }
//
//    val data = sc.parallelize(1 to 100).map(i => new MyData(i, i, "Name" + i.toString))
//    // data is an RDD[MyData]
//
//    data.toHBaseTable("mytable")
//      .inColumnFamily("mycf")
//      .save()
//
//    val read = sc.hbaseTable[MyData]("mytable")
//      .inColumnFamily("mycf")
//    read.foreach(data => {
//      println(data)
//    })
//  }
//}
