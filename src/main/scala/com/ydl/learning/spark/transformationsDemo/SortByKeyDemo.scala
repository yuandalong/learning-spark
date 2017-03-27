package com.ydl.learning.spark.transformationsDemo

/**
  * sortByKey用于针对Key做排序，默认是按照升序排序。
  * Created by ydl on 2017/3/27.
  */
object SortByKeyDemo extends BaseSc with App {
  //创建数据集
  val data = sc.parallelize(List(("A", 2), ("B", 2), ("A", 1), ("B", 1), ("C", 1)))
  //data: org.apache.spark.rdd.RDD[(String, Int)] = ParallelCollectionRDD[30] at parallelize at <console>:27

  //对数据集按照key进行默认排序
  data.sortByKey().collect.foreach(println)
  //res23: Array[(String, Int)] = Array((A,2), (A,1), (B,2), (B,1), (C,1))

  //升序排序
  data.sortByKey(true).collect.foreach(println)
  //res24: Array[(String, Int)] = Array((A,2), (A,1), (B,2), (B,1), (C,1))

  //降序排序
  data.sortByKey(false).collect.foreach(println)
  //res25: Array[(String, Int)] = Array((C,1), (B,2), (B,1), (A,2), (A,1))

}
