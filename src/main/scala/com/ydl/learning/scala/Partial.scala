package com.ydl.learning.scala

/**
  * 部分应用（Partial application）
  *
  * Created by ydl on 2017/11/25.
  */
object Partial extends App {
  //你可以使用下划线“_”部分应用一个函数，结果将得到另一个函数。Scala使用下划线表示不同上下文中的不同事物，
  //你通常可以把它看作是一个没有命名的神奇通配符。在{ _ + 2 }的上下文中，它代表一个匿名参数。你可以这样使用它：
  def adder(m: Int, n: Int) = m + n

  val add2 = adder(2, _: Int)
  println(add2(3))
  //你可以部分应用参数列表中的任意参数，而不仅仅是最后一个。
  val add3 = adder(_: Int, 3)
  println(add3(2))

}
