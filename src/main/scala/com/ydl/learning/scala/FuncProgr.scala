package com.ydl.learning.scala

/**
  * 函数式编程demo
  * Scala中的高阶函数包括两种: 将一个函数作为参数的函数称为高阶函数 ；将一个返回值是函数的函数称为高阶函数.
  *
  * @author ydl
  * @since 2018/11/23
  */
object FuncProgr {
  /** **********函数是参数的高阶函数 *****************/
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

  def demo2(fun: String => List[String]): List[String] = {
    fun("a,b,c")
  }

  def demo3(fun: Unit => List[Int]): List[Int] = {
    fun()
  }

  /** *************返回值是函数的高阶函数 **********************************/
  //定义语法为 val 函数名:(参数类型)=>返回类型 = (参数名:参数类型)=>{函数体}
  //注意无参高阶函数的定义是用()而不是Unit
  val demo4: () => String = () => {
    "123"
  }

  val demo5: (String, String) => String = (a: String, b: String) => {
    a + b
  }

  def main(args: Array[String]): Unit = {
    //高阶函数调用，i是要在函数里用到的变量，类型为高阶函数里函数参数的类型
    demo(1, i => {
      println(i)
      "1" + i
    })

    demo(_ => 1)

    println(demo2(f => {
      f.split(",").toList
    }))

    println(demo3(_ => {
      List(1, 2, 3)
    }))

    println(demo4)
    println(demo4())

    println(demo5)
    println(demo5("a", "b"))
  }
}
