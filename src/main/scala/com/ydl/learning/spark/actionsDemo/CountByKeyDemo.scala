package com.ydl.learning.spark.actionsDemo

import com.ydl.learning.spark.transformationsDemo.BaseSc

/**
  * 统计KV中，相同K的V的个数
  * Created by ydl on 2017/3/27.
  */
object CountByKeyDemo extends BaseSc with App{
  //创建数据集
  var data = sc.parallelize(List(("A",1),("A",2),("B",1)))
//  data: org.apache.spark.rdd.RDD[(String, Int)] = ParallelCollectionRDD[7] at parallelize at <console>:22

  //统计个数
  println(data.countByKey)
//  res9: scala.collection.Map[String,Long] = Map(B -> 1, A -> 2)

}
