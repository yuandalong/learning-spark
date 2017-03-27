package com.ydl.learning.spark.transformationsDemo

/**
  * 在类型为（K,V)和（K,W)的数据集上调用，返回一个 (K, (Seq[V], Seq[W]))元组的数据集。
  * Created by ydl on 2017/3/27.
  */
object CogroupDemo extends BaseSc with App{
  //创建第一个数据集
  var data1 = sc.parallelize(List(("A",1),("B",2),("C",3)))

  //创建第二个数据集
  var data2 = sc.parallelize(List(("A",4)))

  //创建第三个数据集
  var data3 = sc.parallelize(List(("A",4),("A",5)))

  data1.cogroup(data2).collect.foreach(println)
  //输出为 Array[(String, (Iterable[Int], Iterable[Int]))] = Array((B,(CompactBuffer(2),CompactBuffer())), (A,(CompactBuffer(1),CompactBuffer(4))), (C,(CompactBuffer(3),CompactBuffer())))

  data1.cogroup(data3).collect.foreach(println)
  //输出为 Array[(String, (Iterable[Int], Iterable[Int]))] = Array((B,(CompactBuffer(2),CompactBuffer())), (A,(CompactBuffer(1),CompactBuffer(4, 5))), (C,(CompactBuffer(3),CompactBuffer())))
}
