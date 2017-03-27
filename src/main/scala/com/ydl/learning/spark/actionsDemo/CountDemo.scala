package com.ydl.learning.spark.actionsDemo

import com.ydl.learning.spark.transformationsDemo.BaseSc

/**
  * 计算数据集的数据个数，一般都是统计内部元素的个数。
  * Created by ydl on 2017/3/27.
  */
object CountDemo extends BaseSc with App {
  //创建数据集
  var data = sc.parallelize(1 to 3, 1)

  //统计个数
  println(data.count)
  //  res7: Long = 3

  var data2 = sc.parallelize(List(("A", 1), ("B", 1)))
  println(data2.count)
  //  res8: Long = 2

}
