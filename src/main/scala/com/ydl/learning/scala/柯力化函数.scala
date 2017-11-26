package com.ydl.learning.scala

/**
  * 柯里化函数 curried
  *
  * Created by ydl on 2017/11/25.
  */
object 柯力化函数 extends App {
    //有时会有这样的需求：允许别人一会在你的函数上应用一些参数，然后又应用另外的一些参数。

    //例如一个乘法函数，在一个场景需要选择乘数，而另一个场景需要选择被乘数。

    def multiply(m: Int)(n: Int): Int = m * n

    //multiply: (m: Int)(n: Int)Int
    //你可以直接传入两个参数。

    multiply(2)(3)
    //res0: Int = 6
    //你可以填上第一个参数并且部分应用第二个参数。

    val timesTwo = multiply(2) _
    //timesTwo: (Int) => Int = <function1>

    timesTwo(3)
    //res1: Int = 6
    //你可以对任何多参数函数执行柯里化。例如下面的adder函数
    def adder(m: Int, n: Int) = m + n
    (adder _).curried
    //res1: (Int) => (Int) => Int = <function1>
    //下面的_表示匿名参数
    val curriedDef =(adder _).curried
    println(curriedDef(2)(3))

}
