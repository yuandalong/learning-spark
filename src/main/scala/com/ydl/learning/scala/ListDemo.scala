package com.ydl.learning.scala

import scala.util.control.Breaks._

/**
  * 列表demo
  * 列表与数组类似，不过有两点重要的差别1、列表不可变 2、列表具有递归结构（如 linked list）,而数组是连续的
  * Created by ydl on 2017/3/20.
  */
object ListDemo extends App {
    //定义list 调用apply方法
    val fruit = List("apples", "oranges", "pears")
    //剔除第一个元素，返回新列表
    fruit.tail
    val nums = List(1, 2, 3, 4)
    val diag3 =
        List(
            List(1, 0, 0),
            List(0, 1, 0),
            List(0, 0, 1)
        )
    val empty = List()

    //使用::方法构造列表
    //Nil表示空List，顺序从左到右，ap是list里第一个元素
    val fruit2 = "ap" :: ("org" :: ("pes" :: Nil))
    //追加元素
    var fruit3 = List[String]()
    fruit3 = fruit3 :+ "a"
    //根据索引获取元素
    println(fruit3(0))

    //合并两个list
    val fruit4 = fruit3 ++ fruit2
    //替换元素，参数1表示要替换的元素索引，参数2是要替换的元素，用Seq定义，参数3表示要替换几个元素
    fruit3.patch(0, Seq("b"), 1)
    //用123替换两个元素
    fruit2.patch(1, Seq("123"), 2)
    //替换0个元素，就是插入
    fruit2.patch(1, Seq("123"), 0)
    println("----------")
    //正序循环
    fruit2.foreach(println)
    //倒序循环
    for (i <- (0 until fruit2.length).reverse) {
        println("===" + fruit2(i))
    }
    //break使用
    breakable(
        for (i <- 0 until fruit2.length) {
            if (i == 1) {
                break
            }
        }
    )
    val intToTuples = List((1,2),(1,3),(2,3),(3,3),(3,4)).groupBy(_._1)
    println(intToTuples)
}
