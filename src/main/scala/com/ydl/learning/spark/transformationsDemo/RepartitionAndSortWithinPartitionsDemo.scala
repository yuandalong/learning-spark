package com.ydl.learning.spark.transformationsDemo

/**
  * 这个方法是在分区中按照key进行排序，这种方式比先分区再sort更高效，因为相当于在shuffle阶段就进行排序。
  * *
  * 下面的例子中，由于看不到分区里面的数据。可以通过设置分区个数为1，看到排序的效果
  * Created by ydl on 2017/3/27.
  */
object RepartitionAndSortWithinPartitionsDemo extends BaseSc with App {
  val data = sc.parallelize(List((1, 2), (1, 1), (2, 3), (2, 1), (1, 4), (3, 5)), 2)
  //data: org.apache.spark.rdd.RDD[(Int, Int)] = ParallelCollectionRDD[60] at parallelize at <console>:27
  data.repartitionAndSortWithinPartitions(new org.apache.spark.HashPartitioner(2)).collect.foreach(print)
  //  res52: Array[(Int, Int)] = Array((2,3), (2,1), (1,2), (1,1), (1,4), (3,5))
  data.repartitionAndSortWithinPartitions(new org.apache.spark.HashPartitioner(1)).collect.foreach(print)
  //  res53: Array[(Int, Int)] = Array((1,2), (1,1), (1,4), (2,3), (2,1), (3,5))
  data.repartitionAndSortWithinPartitions(new org.apache.spark.HashPartitioner(3)).collect.foreach(print)
  //  res54: Array[(Int, Int)] = Array((3,5), (1,2), (1,1), (1,4), (2,3), (2,1))
}
