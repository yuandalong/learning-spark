package com.ydl.learning.scala

import scala.util.control.Breaks._

/**
  * break demo
  *
  * @author ydl
  * @since 2019-11-19
  */
object BreakDemo {
  def main(args: Array[String]): Unit = {
    //breakable放在循环体外面，相当于java 的break
    breakable {
      for (i <- 0 until 10) {
        println(i)
        if (i > 5) {
          break
        }
      }
    }
    println("---------------------")
    //breakable放在循环体里面，相当于java的continue
    for (i <- 0 until 10) {
      breakable {
        if (i % 2 == 0) {
          break
        }
        println(i)
      }
    }

    println("---------------------")
    val l = List(1, 2, 3, 4, 5, 6, 7, 8)
    var continue = true
    //注意下面两种写法takeWhile的区别
    //第一种，不带stream，takeWhile只执行一次，takeWhile没有break的作用
    //第二种，带stream，以为stream是lazy的，所有foreach每次循环，都会执行一次takeWhile，此时takeWhile才能起到break的作用
    l.takeWhile(_ => continue).foreach(i => {
      println(i)
      if (i > 5) {
        continue = false
      }
    })
    println("---------------------")

    continue = true
    l.toStream.takeWhile(_ => continue).foreach(i => {
      println(i)
      if (i > 5) {
        continue = false
      }
    })

    println("---------------------")

    //for循环加守护标记
    var flag = true
    for(i <- 0 until 10 if flag){
      println(i)
      if(i > 5){
        flag = false
      }
    }

    println("---------------------")
    breakable{
      while (true){
        println(1)
        break
      }
    }

  }
}
