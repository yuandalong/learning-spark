package com.ydl.learning.spark.transformationsDemo

/**
  * filter用于过滤元素信息，仅仅返回满足过滤条件的元素
  * Created by ydl on 2017/3/27.
  */
object FilterDemo extends BaseSc {
  def main(args: Array[String]): Unit = {
    //内容为 Array[Int] = Array(1, 2, 3, 4, 5, 6, 7, 8, 9)
    val data = sc.parallelize(1 to 9, 3)

    //输出内容 Array[Int] = Array(2, 4, 6, 8)
    data.filter(x => x % 2 == 0).collect().foreach(println)
  }
}
