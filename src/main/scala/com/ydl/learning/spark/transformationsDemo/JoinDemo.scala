package com.ydl.learning.spark.transformationsDemo

/**
  * join方法为(K,V)和(K,W)的数据集调用，返回相同的K,所组成的数据集。相当于sql中的按照key做连接。
  * *
  * 有点类似于 select a.value,b.value from a inner join b on a.key = b.key;
  * Created by ydl on 2017/3/27.
  */
object JoinDemo extends BaseSc with App {
  //创建第一个数据集
  var data1 = sc.parallelize(List(("A", 1), ("B", 2), ("C", 3)))

  //创建第二个数据集
  var data2 = sc.parallelize(List(("A", 4)))

  //创建第三个数据集
  var data3 = sc.parallelize(List(("A", 4), ("A", 5)))

  data1.join(data2).collect
  //输出为 Array[(String, (Int, Int))] = Array((A,(1,4)))

  data1.join(data3).collect.foreach(println)
  //输出为 Array[(String, (Int, Int))] = Array((A,(1,4)), (A,(1,5)))
}
