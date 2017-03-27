package com.ydl.learning.spark.transformationsDemo

/**
  * 取两个数据集的交集
  * Created by ydl on 2017/3/27.
  */
object IntersectionDemo extends BaseSc with App {
  //创建第一个数据集
  val data1 = sc.parallelize(1 to 5, 1)

  //创建第二个数据集
  val data2 = sc.parallelize(3 to 7, 1)

  //取交集
  data1.intersection(data2).collect.foreach(println)
  //输出为 Array[Int] = Array(4, 3, 5)
}
