package com.ydl.learning.spark.transformationsDemo

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by ydl on 2017/3/27.
  */
object BaseSc {
  def createSc(appName: String): SparkContext = {
    val conf = new SparkConf().setAppName(appName).setMaster("local[4]")
    new SparkContext(conf)
  }
}

class BaseSc {
  val sc: SparkContext = BaseSc.createSc("app")
}
