package com.ydl.learning.spark.actionsDemo

import com.ydl.learning.spark.transformationsDemo.BaseSc

/**
  * 针对每个参数执行，通常在更新互斥或者与外部存储系统交互的时候使用
  * Created by ydl on 2017/3/27.
  */
object ForeachDemo extends BaseSc with App {
  // 创建数据集
  var data = sc.parallelize(List("b", "a", "e", "f", "c"))
  //  data: org.apache.spark.rdd.RDD[String] = ParallelCollectionRDD[10] at parallelize at <console>:22

  // 遍历
  data.foreach(x => println(x + " hello"))
}
