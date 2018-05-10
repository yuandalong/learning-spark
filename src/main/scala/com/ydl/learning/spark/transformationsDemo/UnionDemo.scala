package com.ydl.learning.spark.transformationsDemo

/**
  * union方法可以合并两个数据集，但是不会去重，仅仅合并而已
  * Created by ydl on 2017/3/27.
  */
object UnionDemo extends BaseSc with App {
  //创建第一个数据集
  val data1 = sc.parallelize(1 to 5, 1)

  //创建第二个数据集
  val data2 = sc.parallelize(3 to 7, 1)

  //取并集
  data1.union(data2).collect.foreach(println)
  //输出为 Array[Int] = Array(1, 2, 3, 4, 5, 3, 4, 5, 6, 7)
}
