package com.ydl.learning.spark.transformationsDemo

/**
  * 这个方法用于计算两个(K,V)数据集之间的笛卡尔积
  * Created by ydl on 2017/3/27.
  */
object CartesianDemo extends BaseSc with App {
  //创建第一个数据集
  var a = sc.parallelize(List(1, 2))

  //创建第二个数据集
  var b = sc.parallelize(List("A", "B"))

  //计算笛卡尔积
  a.cartesian(b).collect.foreach(println)
  //输出结果 res2: Array[(Int, String)] = Array((1,A), (1,B), (2,A), (2,B))
}
