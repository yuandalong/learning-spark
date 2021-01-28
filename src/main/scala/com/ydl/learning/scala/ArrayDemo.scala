package com.ydl.learning.scala

/**
  * scala 数组使用
  */
object ArrayDemo {
  def main(args: Array[String]): Unit = {
    val array = new Array[String](3)
    println(array.length)
    println(array.isEmpty)
    array(0) = "hello"
    array(1) = ", "
    array(2) = "world"
    array.foreach(print)
    println()

    val array2 = Array(1, 2, 3)

    //for循环
    for (i <- array) print(i)
    println()
    //倒序
    for (i <- array.reverse) print(i)
    println()
    //for循环加过滤
    for (i <- array if i == "world") print(i)
    println()

    //for循环返回过滤结果，yield关键字
    def forYield(array: Array[Int]) = {
      for (i <- array if i > 3) yield i
    }

    val intArray = Array(1, 2, 3, 4, 5)
    forYield(intArray).foreach(print) //过滤后的结果

    //定义对象时直接初始化
    val a = Array[String]("a", "b")
    a.foreach(println)

    //  多数据同时循环
    for (a <- array; b <- array2) {
      println(a + " " + b)
    }

    val aa = Array(1)
    val bb = Array(2)
    val cc = Array.concat(aa,bb)
    println(cc)

  }
}