package com.ydl.learning.spark.transformationsDemo

/**
  * 这个方法用于根据key作分组计算，但是它跟reduce不同，它还是属于transfomation的方法。
  * Created by ydl on 2017/3/27.
  */
object ReduceByKeyDemo extends BaseSc with App {
  //创建数据集
  val data = sc.parallelize(List(("A", 1), ("A", 1), ("A", 2), ("B", 1)))

  data.reduceByKey((x, y) => x + y).collect.foreach(println)
  //输出为 Array[(String, Int)] = Array((B,1), (A,4))
}
