package com.ydl.learning.spark.actionsDemo

import com.ydl.learning.spark.transformationsDemo.BaseSc

/**
  * 返回数据集的所有元素，通常是在使用filter或者其他操作的时候，返回的数据量比较少时使用。
  * Created by ydl on 2017/3/27.
  */
object CollectDemo extends BaseSc with App {
  //创建数据集
  var data = sc.parallelize(1 to 3, 1)

  data.collect.foreach(println)
  //  res6: Array[Int] = Array(1, 2, 3)
}
