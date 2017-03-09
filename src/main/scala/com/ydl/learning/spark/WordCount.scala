package com.ydl.learning.spark

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD.rddToPairRDDFunctions

object WordCount {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("wordCount").setMaster("local")
    val sc = new SparkContext(conf)
    val input = sc.textFile("/Users/ydl/work/soft/spark-2.0.2-bin-hadoop2.7/README.MD")
    val words = input.flatMap { line => line.split(" ") }
    val counts = words.map(word => (word, 1)).reduceByKey { case (x, y) => x + y }
    println(counts.collect().mkString(","))
  }
}   