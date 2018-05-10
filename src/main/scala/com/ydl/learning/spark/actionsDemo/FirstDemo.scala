package com.ydl.learning.spark.actionsDemo

import com.ydl.learning.spark.transformationsDemo.BaseSc

/**
  * 返回数据集的第一个元素，类似take(1)
  * Created by ydl on 2017/3/27.
  */
object FirstDemo extends BaseSc with App {
  //创建数据集
  var data = sc.parallelize(List(("A", 1), ("B", 1)))

  //获取第一条元素
  println(data.first)
  //  res9: (String, Int) = (A,1)

}
