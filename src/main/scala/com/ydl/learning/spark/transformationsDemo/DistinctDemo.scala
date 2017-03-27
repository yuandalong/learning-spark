package com.ydl.learning.spark.transformationsDemo

/**
  * 这个方法用于对本身的数据集进行去重处理
  * Created by ydl on 2017/3/27.
  */
object DistinctDemo extends BaseSc with App {
  //创建数据集
  val data = sc.parallelize(List(1, 1, 1, 2, 2, 3, 4), 1)

  //执行去重
  data.distinct.collect.foreach(println)
  //输出为 Array[Int] = Array(4, 1, 3, 2)

  //如果是键值对的数据，kv都相同，才算是相同的元素
  val data2 = sc.parallelize(List(("A", 1), ("A", 1), ("A", 2), ("B", 1)))

  //执行去重
  data2.distinct.collect.foreach(println)
  //输出为 Array[(String, Int)] = Array((A,1), (B,1), (A,2))
}
