package com.ydl.learning.spark.transformationsDemo

/**
  * 这个方法属于宽依赖的方法，针对所有的kv进行分组，可以把相同的k的聚合起来。如果要想计算sum等操作，
  * 最好使用reduceByKey或者combineByKey
  * Created by ydl on 2017/3/27.
  */
object GroupByKeyDemo extends BaseSc with App {
  //创建数据集
  val data = sc.parallelize(List(("A", 1), ("A", 1), ("A", 2), ("B", 1)))

  //分组输出
  data.groupByKey.collect.foreach(println)
  //输出为 Array[(String, Iterable[Int])] = Array((B,CompactBuffer(1)), (A,CompactBuffer(1, 1, 2)))

}
