package com.ydl.learning.spark.streaming

import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.Seconds
import org.apache.spark.SparkConf
import java.io.PrintWriter
import java.io.File
import org.apache.spark.examples.streaming.StreamingExamples

/**
  * spark stream demo
  * nc -lk 9999 开启本机9999端口之后发送消息
  */
object StreamDemo { //scala 2.9以后的版本废弃了Application而是启用了App类
  def main(args: Array[String]) {
    StreamingExamples.setStreamingLogLevels()
    //注意setMaster，用spark-submit提交jar包时不要用local，否则会遇到各种各样的问题，例如print不打印
    val conf = new SparkConf().setAppName("stream")
    if (args.length > 0) {
      println("master is " + args(0))
      conf.setMaster(args(0))
    }
    else
      conf.setMaster("local[4]")
    // 从SparkConf创建StreamingContext并指定1秒钟的批处理大小  
    val ssc = new StreamingContext(conf, Seconds(5))
    // 连接到本地机器9999端口上后，使用收到的数据创建DStream 
    val lines = ssc.socketTextStream("127.0.0.1", 20102)
    lines.print()
    println(lines.toString())
    val words = lines.flatMap(_.split(" "))
    words.print()
    // 从DStream中筛选出包含字符串"error"的行
    val errorLines = lines.filter(_.contains("error")) // 打印出有"error"的行
    errorLines.print()
    // 启动流计算环境StreamingContext并等待它"完成" 
    ssc.start()
    // 等待作业完成
    ssc.awaitTermination()
  }

}