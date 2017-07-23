package com.ydl.learning.scala

/**
  * Created by ydl on 2017/3/15.
  */
object StringDemo extends App{
  val name = "ydl"
  val age = 30
  //字符串格式化
  println("%s is %d".format(name,age))
  println(s"$name is $age")
  println(name.equals("ydl"))
  if(name.equals("ydl"))
    println("adfadf")
}
