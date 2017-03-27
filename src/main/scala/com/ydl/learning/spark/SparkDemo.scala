package com.ydl.learning.spark

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by ydl on 2017/3/21.
  */
object SparkDemo extends App {
  val conf = new SparkConf().setAppName("wordCount").setMaster("local[2]")
  val sc = new SparkContext(conf)
  // 设置3个分区
  val a = sc.parallelize(1 to 1000, 3)

  def myfunc[T](iter: Iterator[T]): Iterator[(T, T)] = {
    var res = List[(T, T)]()
    var pre = iter.next
    while (iter.hasNext) {
      val cur = iter.next;
      res.::=(pre, cur)
      pre = cur;
    }
    res.iterator
  }

  a.mapPartitions(myfunc).collect
}
