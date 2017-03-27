package com.ydl.learning.spark.transformationsDemo

/**
  * 这个方法与mapPartitions相同，只不过多提供了一个Index参数
  * Created by ydl on 2017/3/27.
  */
object MapPartitionsWithIndexDemo extends BaseSc {
  def main(args: Array[String]): Unit = {
    //首先创建三个分区
    val data = sc.parallelize(1 to 9, 3)
    //输出为 Array[Int] = Array(1, 2, 3, 4, 5, 6, 7, 8, 9)

    //查看分区的个数
    println(data.partitions.size)
    //输出为 Int = 3

    val result = data.mapPartitionsWithIndex {
      (x, iter) => {
        val result = List[String]()
        var i = 0
        while (iter.hasNext) {
          i += iter.next()
        }
        result.::(x + "|" + i).iterator
      }
    }

    result.collect.foreach(println)
    //输出结果为 Array[String] = Array(0|6, 1|15, 2|24)
  }
}
