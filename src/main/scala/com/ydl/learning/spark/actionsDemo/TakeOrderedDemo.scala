package com.ydl.learning.spark.actionsDemo

import com.ydl.learning.spark.transformationsDemo.BaseSc

/**
  * 基于内置的排序规则或者自定义的排序规则排序，返回前n个元素
  * Created by ydl on 2017/3/27.
  */
object TakeOrderedDemo extends BaseSc with App{
  //创建数据集
  var data = sc.parallelize(List("b","a","e","f","c"))
//  data: org.apache.spark.rdd.RDD[String] = ParallelCollectionRDD[3] at parallelize at <console>:21

  //返回排序数据
  data.takeOrdered(3).foreach(println)
//  res4: Array[String] = Array(a, b, c)
}
