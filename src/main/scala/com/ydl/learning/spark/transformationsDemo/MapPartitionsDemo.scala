package com.ydl.learning.spark.transformationsDemo

/**
  * mapPartitions与map类似，只不过每个元素都是一个分区的迭代器，因此内部可以针对分区为单位进行处理
  * Created by ydl on 2017/3/27.
  */
object MapPartitionsDemo extends BaseSc {
  def main(args: Array[String]): Unit = {
    val data = sc.parallelize(1 to 9, 3) //3个分区
    println(data.partitions.size) //查询分区个数
    val result = data.mapPartitions { x => {
      val res = List[Int]()
      var i = 0
      while (x.hasNext) {
        i += x.next()
      }
      res.::(i).iterator
    }
    }
    result.collect.foreach(println)
  }
}
