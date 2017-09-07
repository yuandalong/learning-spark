package com.ydl.learning.scala

/**
  * 列表demo
  * 列表与数组类似，不过有两点重要的差别1、列表不可变 2、列表具有递归结构（如 linked list）,而数组是连续的
  * Created by ydl on 2017/3/20.
  */
object ListDemo extends App{
  //定义list 调用apply方法
  val fruit = List("apples","oranges","pears")
  val nums = List(1,2,3,4)
  val diag3 =
    List(
      List(1,0,0),
      List(0,1,0),
      List(0,0,1)
    )
  val empty = List()

  //使用::方法构造列表
  //Nil表示空List，顺序从左到右，ap是list里第一个元素
  val fruit2 = "ap" :: ("org" :: ("pes" :: Nil))
  //追加元素
  var fruit3 = List[String]()
  fruit3 = fruit3 :+ "a"


}
