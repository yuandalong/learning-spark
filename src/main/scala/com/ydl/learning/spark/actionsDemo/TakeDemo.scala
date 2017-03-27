package com.ydl.learning.spark.actionsDemo

import com.ydl.learning.spark.transformationsDemo.BaseSc

/**
  * 返回数组的头n个元素
  * Created by ydl on 2017/3/27.
  */
object TakeDemo extends BaseSc with App {
  //创建数据集
  var data = sc.parallelize(List(("A", 1), ("B", 1)))

  println(data.take(1).mkString("|"))
  //  res10: Array[(String, Int)] = Array((A,1))

  //如果n大于总数，则会返回所有的数据
  println(data.take(8).mkString("|"))
  //  res12: Array[(String, Int)] = Array((A,1), (B,1))

  //如果n小于等于0，会返回空数组
  println(data.take(-1).mkString("|"))
  //  res13: Array[(String, Int)] = Array()

  println(data.take(0).mkString("|"))
  //  res14: Array[(String, Int)] = Array()
}
