package com.ydl.learning.spark.actionsDemo

import com.ydl.learning.spark.transformationsDemo.BaseSc

/**
  * 基于Java序列化保存文件
  * Created by ydl on 2017/3/27.
  */
object SaveAsObjectFile extends BaseSc with App {
  var data = sc.parallelize(List("a", "b", "c"), 1)
  //  data: org.apache.spark.rdd.RDD[String] = ParallelCollectionRDD[16] at parallelize at <console>:22

  data.saveAsObjectFile("str_test")
  //TODO 抛出异常 java.lang.ArrayStoreException: java.lang.String
  var data2 = sc.objectFile[Array[String]]("str_test")
  //  data2: org.apache.spark.rdd.RDD[Array[String]] = MapPartitionsRDD[20] at objectFile at <console>:22

  data2.collect.foreach(println)
}
