package com.ydl.learning.spark.transformationsDemo

/**
  * flatMap与map相比，不同的是可以输出多个结果
  * Created by ydl on 2017/3/27.
  */
object FlatMapDemo extends BaseSc {
  def main(args: Array[String]): Unit = {
    val data = sc.parallelize(1 to 4, 1)
    //输出内容为 Array[Int] = Array(1, 2, 3, 4)

    data.flatMap(x => 1 to x).collect().foreach(println)
    //输出内容为 Array[Int] = Array(1, 1, 2, 1, 2, 3, 1, 2, 3, 4)
  }
}
