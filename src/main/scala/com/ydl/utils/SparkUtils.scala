package com.ydl.utils

import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.{immutable, mutable}

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
    *
    * @param appName
    * @param master
    * @return
    */
  def getContext(appName: String, master: String): SparkContext = {
    val conf = getConf(appName, master, Map())
    new SparkContext(conf)
  }

  def getContext(appName: String, master: String, map: Map[String, String]): SparkContext = {
    val conf = getConf(appName, master, map)
    new SparkContext(conf)
  }

  /**
    * 创建spark conf
    *
    * @param appName
    * @param master
    * @param map 参数键值对
    * @return
    */
  def getConf(appName: String, master: String, map: Map[String, String]): SparkConf = {
    val conf = new SparkConf()
      .setAppName(appName)
      .setMaster(master)
    map.foreach(keyValue => {
      conf.set(keyValue._1, keyValue._2)
    })
    conf
  }

  def main(args: Array[String]): Unit = {
    getConf("1", "local[2]", Map("1" -> "2"))
    getContext("1", "local[2]", Map("1" -> "2"))
  }
}
