package com.ydl.learning.spark.actionsDemo

import com.ydl.learning.spark.transformationsDemo.BaseSc

/**
  * 这个方法与sample还是有一些不同的，主要表现在：
  * *
  * 返回具体个数的样本（第二个参数指定）
  * 直接返回array而不是RDD
  * 内部会将返回结果随机打散
  * Created by ydl on 2017/3/27.
  */
object TakeSampleDemo extends BaseSc with App {
  //创建数据集
  var data = sc.parallelize(List(1, 3, 5, 7))
  //  data: org.apache.spark.rdd.RDD[Int] = ParallelCollectionRDD[0] at parallelize at <console>:21

  //随机2个数据
  println(data.takeSample(true, 2, 1).mkString(" "))
  //  res0: Array[Int] = Array(7, 1)

  //随机4个数据，注意随机的数据可能是重复的
  println(data.takeSample(true, 4, 1).mkString(" "))
  //  res1: Array[Int] = Array(7, 7, 3, 7)

  //第一个参数是是否重复
  println(data.takeSample(false, 4, 1).mkString(" "))
  //  res2: Array[Int] = Array(3, 5, 7, 1)

  println(data.takeSample(false, 5, 1).mkString(" "))
  //  res3: Array[Int] = Array(3, 5, 7, 1)

}
