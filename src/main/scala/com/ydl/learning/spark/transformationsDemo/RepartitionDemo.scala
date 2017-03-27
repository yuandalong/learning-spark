package com.ydl.learning.spark.transformationsDemo

/**
  * 这个方法作用于coalesce一样，重新对RDD进行分区，相当于shuffle版的calesce
  * Created by ydl on 2017/3/27.
  */
object RepartitionDemo extends BaseSc with App {
  //创建数据集
  var data = sc.parallelize(1 to 9, 3)
  //data: org.apache.spark.rdd.RDD[Int] = ParallelCollectionRDD[6] at parallelize at <console>:27

  //查看分区的大小
  println(data.partitions.size)
  //res3: Int = 3

  var result = data.repartition(2)
  //result: org.apache.spark.rdd.RDD[Int] = MapPartitionsRDD[27] at repartition at <console>:29

  println(result.partitions.length)
  //  res16: Int = 2

  println(result.toDebugString)
  //    res17: String =
  //    (2) MapPartitionsRDD[27] at repartition at <console>:29 []
  //      |  CoalescedRDD[26] at repartition at <console>:29 []
  //        |  ShuffledRDD[25] at repartition at <console>:29 []
  //          +-(3) MapPartitionsRDD[24] at repartition at <console>:29 []
  //            |  ParallelCollectionRDD[9] at parallelize at <console>:27 []
}
