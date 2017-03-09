package com.ydl.learning.spark.sql

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.SQLContext

/**
 * spark sql demo
 */
object SqlDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("wordCount")
    if (args.length > 0) {
      conf.setMaster(args(0))
    }
    val sc = new SparkContext(conf)
    val sqlCtx = new SQLContext(sc)
    val input = sqlCtx.read.json("/Users/ydl/work/git/learning-spark/files/testweet.json")
    input.createOrReplaceTempView("tweets")//注册临时表名
    val topTweets = sqlCtx.sql("select text,retweetCount from tweets order by retweetCount limit 10")//执行sql
    topTweets.foreach { x => print(x(0)+"|"+x(1)) }
  }
}