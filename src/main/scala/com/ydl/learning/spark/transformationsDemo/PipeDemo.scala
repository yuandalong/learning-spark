package com.ydl.learning.spark.transformationsDemo

/**
  * pipe方法用于针对每个分区的RDD执行一个shell脚本命令，
  * 可以使perl或者bash。分区的元素将会被当做输入，脚本的输出则被当做返回的RDD值
  * Created by ydl on 2017/3/27.
  */
object PipeDemo extends BaseSc with App {
  //创建数据集
  var data = sc.parallelize(1 to 9, 3)
  //data: org.apache.spark.rdd.RDD[Int] = ParallelCollectionRDD[34] at parallelize at <console>:27

  //测试脚本
  data.pipe("head -n 1").collect.foreach(println)
  //res26: Array[String] = Array(1, 4, 7)

  data.pipe("tail -n 1").collect.foreach(println)
  //res27: Array[String] = Array(3, 6, 9)

  data.pipe("tail -n 2").collect.foreach(println)
  //res28: Array[String] = Array(2, 3, 5, 6, 8, 9)
}
