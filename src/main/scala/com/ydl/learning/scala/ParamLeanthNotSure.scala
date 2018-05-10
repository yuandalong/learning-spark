package com.ydl.learning.scala

/**
  * 可变长度参数
  *
  * Created by ydl on 2017/11/25.
  */
object ParamLeanthNotSure extends App {
  //这是一个特殊的语法，可以向方法传入任意多个同类型的参数。例如要在多个字符串上执行String的capitalize函数，可以这样写
  def capitalizeAll(args: String*) = {
    args.map { arg =>
      println(arg.capitalize)
    }
  }

  capitalizeAll("rarity", "applejack")
}
