package com.ydl.learning.scala

/**
  * scala中函数求值有两种策略，
  * （1）call-by-value是在调用函数"之前"计算；
  * （2）call-by-name是在需要时计算
  *
  * call-by-name使用场景：
  * 如主方法中有一堆前置条件，入参函数不一定会执行，此时就使用与call-by-name，
  * 在条件满足的时候才调用方法进行计算
  *
  * @author ydl
  * @since 2019/3/2
  */
object CallByName {

  def func(): Int = {
    println("call method")
    1
  }

  /**
    * call by value策略，就是普通方法
    *
    * @param func
    */
  def callByValue(func: Int): Unit = {
    println("call by value")
    println("value = " + func)
    println("value = " + func)
  }

  /**
    * call by name策略，注意入参名和类型之间的 =>，其中空格是必须的
    *
    * @param func
    */
  def callByName(func: => Int): Unit = {
    println("call by name")
    println("value = " + func)
    println("value = " + func)
  }

  def main(args: Array[String]): Unit = {
    println("call by value:")
    callByValue(func())
    println()
    println("call by name")
    callByName(func())
  }

}
