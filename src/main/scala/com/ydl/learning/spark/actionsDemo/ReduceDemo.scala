package com.ydl.learning.spark.actionsDemo

import com.ydl.learning.spark.transformationsDemo.BaseSc

/**
  * 这个方法会传入两个参数，计算这两个参数返回一个结果。返回的结果与下一个参数一起当做参数继续进行计算。
  * Created by ydl on 2017/3/27.
  */
object ReduceDemo extends BaseSc with App {
  //创建数据集
  var data = sc.parallelize(1 to 3, 1)

  data.collect
  //  res6: Array[Int] = Array(1, 2, 3)

  //collect计算
  print(data.reduce((x, y) => x + y))
  //  res5: Int = 6
}
