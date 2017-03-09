package com.ydl.learning.spark

import java.sql.DriverManager
import java.sql.ResultSet
import java.util.Properties
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.rdd.JdbcRDD

/**
 * 数据库操作
 */
object JDBC {
  def main(args: Array[String]): Unit = {

    def createConnection() = {
      Class.forName("org.postgresql.Driver").newInstance()
      val info = new Properties();
      info.put("user", "postgres")
      info.put("password", "Password123")
      DriverManager.getConnection("jdbc:postgresql://supershouyin.pg.rds.aliyuncs.com:3433/cloud_rpt_pre", info)
    }
    def extractValues(r: ResultSet) = {
//      (r.getString(1), r.getString(2))
      (r.getString(1))
    }
    val conf = new SparkConf().setAppName("JDBC").setMaster("local")
    val sc = new SparkContext(conf)
    //lowerBound upperBound作用查看JdbcRDD的注释
    val data = new JdbcRDD(sc, createConnection, "select * from cloud_rpt.acc_bill limit ? offset ?", lowerBound = 100,
      upperBound = 0, numPartitions = 1,extractValues)
//    println(data.collect().toList)
    data.foreach { x => println(x) }
  }
}