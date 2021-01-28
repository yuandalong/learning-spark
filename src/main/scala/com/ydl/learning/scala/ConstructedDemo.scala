package com.ydl.learning.scala

/**
  * 构造函数
  * 类名后面加括号带参数的形式定义主构造函数
  *
  * @author ydl
  * @since 2020/9/26
  */
class ConstructedDemo(a: String) {
  /**
    * 扩展构造函数
    *
    * @param b
    * @param c
    */
  def this(b: String, c: String) = {
    //扩展构造函数第一行必须调用主构造函数
    this("a")
  }

}

/**
  * 父类定义了构造函数时，子类通过下面的语法集成父类，直接把子类构造函数入参传给父类
  *
  * @param a
  */
class Child(a: String) extends ConstructedDemo(a) {

}
