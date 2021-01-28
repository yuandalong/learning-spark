package com.ydl.learning.scala

import scala.util.control.Breaks._

/**
  * 列表demo
  * 列表与数组类似，不过有两点重要的差别1、列表不可变 2、列表具有递归结构（如 linked list）,而数组是连续的
  * 四种操作符:: , +:, :+, :::, +++的区别
  * 1）:: 该方法被称为cons，意为构造，向队列的头部追加数据，创造新的列表。用法为 x::list,其中x为加入到头部的元素，无论x是列表与否，它都只将成为新生成列表的第一个元素，也就是说新生成的列表长度为list的长度＋1(btw, x::list等价于list.::(x))
  * 2）:+和+: 两者的区别在于:+方法用于在尾部追加元素，+:方法用于在头部追加元素，和::很类似，但是::可以用于pattern match ，而+:则不行. 关于+:和:+,只要记住冒号永远靠近集合类型就OK了。
  * 3）++ 该方法用于连接两个集合，list1++list2
  * 4）::: 该方法只能用于连接两个List类型的集合
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
  //此时fruit3不变，需要重新赋值才行
  fruit3 :+ "a"
  fruit3 = fruit3 :+ "a"
  //根据索引获取元素
  println(fruit3.head)

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
  for (i <- fruit2.indices.reverse) {
    println("===" + fruit2(i))
  }
  //break使用
  breakable(
    for (i <- fruit2.indices) {
      if (i == 1) {
        break
      }
    }
  )
  val intToTuples = List((1, 2), (1, 3), (2, 3), (3, 3), (3, 4)).groupBy(_._1)
  println(intToTuples)

  //fold和reduce，reduce是一种特殊的fold，fold需要初始值，reduce不需要
  val numbers = List((5, 1), (4, 2), (8, 3), (6, 4), (2, 5))
  val result = numbers.fold(0, 0) { (z, i) =>
    (z._1 + i._1, z._2 + i._2)
  }
  println(result)
  val result2 = numbers.reduce((a, b) => {
    (a._1 + b._1, a._2 + b._2)
  })
  println(result2)
  //排序
  //sorted 元素按字段排序，默认降序
  numbers.sorted.foreach(println)
  numbers.sorted.foreach(println)
  println()
  //sortBy 元素按指定属性排序
  numbers.sortBy(_._2).foreach(println)
  //通过柯立话函数指定排序规则
  numbers.sortBy(_._2)(Ordering.Int.reverse).foreach(println)
  println()
  //sortWith 元素按指定函数排序
  numbers.sortWith((a, b) => {
    a._1 > b._1
  }).foreach(println)
  println()
  val numbers2 = List((5, 6), (4, 3))
  numbers.zipWithIndex.foreach(a => {
    println(a._1 + " " + a._2)
  })

  //取前几个元素
  numbers.take(2)

  //取最大
  numbers.maxBy(_._2)
}
