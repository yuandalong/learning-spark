package com.ydl.utils

import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Spark工具类
  *
  * Created by ydl on 2018/1/10.
  */
object SparkUtils {
    /**
      * 创建spark session
      *
      * @param appName
      * @param master
      * @return
      */
    def getSession(appName: String, master: String): SparkSession = {
        SparkSession
                .builder()
                .appName(appName)
                .master(master)
                //.config("spark.some.config.option", "some-value")
                .getOrCreate()
    }

    /**
      * 创建 spark context
      * @param appName
      * @param master
      * @return
      */
    def getContext(appName: String, master: String): SparkContext = {
        val conf = new SparkConf()
                .setAppName(appName)
                .setMaster(master)
        new SparkContext(conf)
    }
}
