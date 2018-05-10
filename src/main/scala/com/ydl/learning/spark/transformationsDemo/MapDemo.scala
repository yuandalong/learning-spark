package com.ydl.learning.spark.transformationsDemo

/**
  * map用于遍历rdd中的每个元素，可以针对每个元素做操作处理
  * Created by ydl on 2017/3/27.
  */
object MapDemo extends BaseSc {
  def main(args: Array[String]): Unit = {
    //内容为 Array[Int] = Array(1, 2, 3, 4, 5, 6, 7, 8, 9)
    val data = sc.parallelize(1 to 9, 2)
    //输出内容 Array[Int] = Array(2, 4, 6, 8, 10, 12, 14, 16, 18)
    data.map(x => x * 2).collect().foreach(println)
  }
}
