package com.ydl.learning.scala

/**
  * 元组demo
  * Map和Tuple的区别是Map是键值对的集合，元组则是不同类型值的聚集
  * Created by ydl on 2017/3/20.
  */
object TupleDemo extends App {
  //元组的定义
  ("hello", "china", "beijing")
  //res23: (String, String, String) = (hello,china,beijing)

  ("hello", "china", 1)
  //res24: (String, String, Int) = (hello,china,1)

  var tuple = ("Hello", "China", 1)
  //tuple: (String, String, Int) = (Hello,China,1)

  //访问元组内容
  tuple._1
  //res25: String = Hello

  tuple._2
  //res26: String = China

  tuple._3
  //res27: Int = 1

  //通过模式匹配获取元组内容
  val (first, second, third) = tuple
  println(first)
  println(second)
  println(third)
}
