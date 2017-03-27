package com.ydl.learning.spark.actionsDemo

import com.ydl.learning.spark.transformationsDemo.BaseSc

/**
  * 保存为sequence文件
  * Created by ydl on 2017/3/27.
  */
object SaveAsSequenceFileDemo extends BaseSc with App {
  var data = sc.parallelize(List(("A", 1), ("A", 2), ("B", 1)), 3)
  //  data: org.apache.spark.rdd.RDD[(String, Int)] = ParallelCollectionRDD[21] at parallelize at <console>:22

  data.saveAsSequenceFile("kv_test")
}
