package com.ydl.learning.spark.transformationsDemo

/**
  * aggregateByKey算子，参数为(zeroValue: U)(seqOp: (U, V) => U, combOp: (U, U) => U)
  * aggregateByKey与aggregate类似，区别是根据key进行分组合并，也是seqOp相当于map操作，comb相当于reduce
  * Created by ydl on 2017/3/27.
  */
object AggregateByKeyDemo extends BaseSc with App {
  var data = sc.parallelize(List((1, 1), (1, 2), (1, 3), (2, 4)), 2)
  //data: org.apache.spark.rdd.RDD[(Int, Int)] = ParallelCollectionRDD[54] at parallelize at <console>:27

  def sum(a: Int, b: Int): Int = {
    a + b
  }

  //sum: (a: Int, b: Int)Int

  data.aggregateByKey(0)(sum, sum).collect.foreach(println)

  //res42: Array[(Int, Int)] = Array((2,4), (1,6))

  def max(a: Int, b: Int): Int = {
    math.max(a, b)
  }

  //max: (a: Int, b: Int)Int

  data.aggregateByKey(0)(max, sum).collect.foreach(println)
  //res44: Array[(Int, Int)] = Array((2,4), (1,5))
}
