package com.ydl.learning.scala

/**
  * 函数式编程demo
  *
  * @author ydl
  * @since 2018/11/23
  */
object FuncProgr {
  /**
    * 高阶函数，入参有函数
    *
    * @param a
    * @param fun 函数，定义格式为 函数名:函数参数类型 => 函数返回类型
    */
  def demo(a: Int, fun: Int => String): Unit = {
    println(fun(a))
  }

  def demo(fun: String => Int): Unit = {
    println("demo")
    println(fun("demo"))
  }

  def main(args: Array[String]): Unit = {
    //高阶函数调用，i是要在函数里用到的变量，类型为高阶函数里函数参数的类型
    demo(1, i => {
      println(i)
      "1" + i
    })

    demo(_ => 1)
  }
}
