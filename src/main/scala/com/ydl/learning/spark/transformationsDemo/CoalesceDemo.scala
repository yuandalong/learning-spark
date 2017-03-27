package com.ydl.learning.spark.transformationsDemo

/**
  * 这个方法用于对RDD进行重新分区，第一个参数是分区的数量，第二个参数是是否进行shuffle
  * Created by ydl on 2017/3/27.
  */
object CoalesceDemo extends BaseSc with App {
  //创建数据集
  var data = sc.parallelize(1 to 9, 3)
  //data: org.apache.spark.rdd.RDD[Int] = ParallelCollectionRDD[6] at parallelize at <console>:27

  //查看分区的大小
  data.partitions.size
  //res3: Int = 3

  //不使用shuffle重新分区
  var result = data.coalesce(2, false)
  // result: org.apache.spark.rdd.RDD[Int] = CoalescedRDD[19] at coalesce at <console>:29

  println(result.partitions.length)
  //  res12: Int = 2

  println(result.toDebugString)
  //  res13: String =
  //  (2) CoalescedRDD[19] at coalesce at <console>:29 []
  //   |  ParallelCollectionRDD[9] at parallelize at <console>:27 []

  //使用shuffle重新分区
  var result2 = data.coalesce(2, true)
  //  result: org.apache.spark.rdd.RDD[Int] = MapPartitionsRDD[23] at coalesce at <console>:29

  println(result2.partitions.length)
  //    res14: Int = 2

  println(result2.toDebugString)
  //   res15: String =
  //CollectionRDD[9] at parallelize at <console>:27 []
}
