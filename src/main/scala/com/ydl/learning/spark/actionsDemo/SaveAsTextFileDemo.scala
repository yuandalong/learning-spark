package com.ydl.learning.spark.actionsDemo

import com.ydl.learning.spark.transformationsDemo.BaseSc

/**
  * 将数据集作为文本文件保存到指定的文件系统、hdfs、或者hadoop支持的其他文件系统中。
  * Created by ydl on 2017/3/27.
  */
object SaveAsTextFileDemo extends BaseSc with App {
  //创建数据集
  var data = sc.parallelize(List("b", "a", "e", "f", "c"))
  //  data: org.apache.spark.rdd.RDD[String] = ParallelCollectionRDD[3] at parallelize at <console>:21

  //保存为test_data_save文件
  data.saveAsTextFile("test_data_save")//保存到项目根目录test_data_save目录下，会根据分区个数拆分文件
  //引入必要的class
  import org.apache.hadoop.io.compress.GzipCodec
  //保存为压缩文件
  data.saveAsTextFile("test_data_save2", classOf[GzipCodec])
}
