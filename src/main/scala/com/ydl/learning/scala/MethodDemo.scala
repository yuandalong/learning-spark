package com.ydl.learning.scala

/**
  * scala函数demo
  * Created by ydl on 2017/3/14.
  */
object MethodDemo extends App {
  /*
   函数字面量 function literal
   =>左侧的表示输入，右侧表示转换操作
*/
  val increase = (x: Int) => x + 1
  println(increase(10))

  //前面的语句等同于
  def increaseAnother(x: Int) = x + 1

  println(increaseAnother(10))

  //多个语句则使用{}
  val increase2 = (x: Int) => {
    println("Xue")
    println("Tu")
    println("Wu")
    println("You")
    x + 1
  }
  println(increase2(10))

  //数组的map方法中调用（写法1）
  println(Array(1, 2, 3, 4).map(increase).mkString(","))

  //匿名函数写法（写法2）
  println(Array(1, 2, 3, 4).map((x: Int) => x + 1).mkString(","))

  //花括方式（写法3）
  Array(1, 2, 3, 4).map { (x: Int) => x + 1 }.mkString(",")

  //省略.的方式（写法4)
  Array(1, 2, 3, 4) map { (x: Int) => x + 1 } mkString (",")

  //参数类型推断写法（写法5）
  Array(1, 2, 3, 4) map { (x) => x + 1 } mkString (",")

  //函数只有一个参数的话，可以省略()（写法6）
  Array(1, 2, 3, 4) map { x => x + 1 } mkString (",")

  //如果参数右边只出现一次，则可以进一步简化（写法7）
  Array(1, 2, 3, 4) map {
    _ + 1
  } mkString (",")


  //值函数简化方式
  //val fun0=1+_，该定义方式不合法，因为无法进行类型推断
  // val fun0 = 1 + _

  //值函数简化方式（正确方式）
  val fun1 = 1 + (_: Double)

  fun1(999)

  //值函数简化方式（正确方式2）
  val fun2: (Double) => Double = 1 + _

  fun2(200)

  //函数参数(高阶函数）
  //((Int)=>String)=>String
  def convertIntToString(f: (Int) => String) = f(4)

  convertIntToString((x: Int) => x + " s")

  //高阶函数可以产生新的函数
  //(Double)=>((Double)=>Double)
  def multiplyBy(factor: Double) = (x: Double) => factor * x

  val x = multiplyBy(10)

  x(50)


  //闭包(Closure）
  //(x:Int)=>x+more,这里面的more是一个自由变量（Free Variable）,more是一个没有给定含义的不定变量
  //而x则的类型确定、值在函数调用的时候被赋值，称这种变量为绑定变量（Bound Variable）
  (x: Int) => x + more
  var more = 1

  val fun = (x: Int) => x + more

  fun(10)

  more = 10

  fun(10)

  //像这种运行时确定more类型及值的函数称为闭包,more是个自由变量，在运行时其值和类型得以确定
  //这是一个由开放(free)到封闭的过程，因此称为闭包

  val someNumbers = List(-11, -10, -5, 0, 5, 10)

  var sum = 0

  someNumbers.foreach(sum += _)

  sum

  someNumbers.foreach(sum += _)

  sum

  //下列函数也是一种闭包，因为在运行时其值才得以确定
  def multiplyBy2(factor: Double) = (x: Double) => factor * x
}
