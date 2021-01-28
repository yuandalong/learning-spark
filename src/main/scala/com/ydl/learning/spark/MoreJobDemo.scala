package com.ydl.learning.spark

import java.util.concurrent.{Callable, Executors}

import org.apache.spark.sql.SparkSession

/**
  * 多job并行处理
  * https://blog.csdn.net/zwgdft/article/details/88349295
  * https://www.cnblogs.com/yyy-blog/p/10530795.html
  *
  * @author ydl
  * @since 2020/9/24
  */
object MoreJobDemo {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("name")
      .master("local[2]")
      .getOrCreate()

    val df = spark.read.json("src\\main\\resources\\json.txt")

    df.show()


    //没有多线程处理的情况，连续执行两个Action操作，生成两个Job
    df.rdd.saveAsTextFile("")
    df.rdd.saveAsTextFile("")


    //用Executor实现多线程方式处理Job
    val dfList = Array(df,df)
    val executorService = Executors.newFixedThreadPool(2)
    for(df <- dfList) {
      executorService.submit(new Callable[Boolean]() {
        def call() : Boolean  = {
          df.show()
          true
        }
      })
    }

    executorService.shutdown()

    spark.stop()
  }
}
