package com.ydl.learning.scala

/**
  * Scala的By-Name参数
  *
  * @author ydl
  * @since 2018/6/22
  */
object ByNameParam {
  def main(args: Array[String]): Unit = {
    def time(): Long = {
      println("Getting time in nano seconds")
      System.nanoTime
    }

    /**
      * 普通参数方法，非懒加载
      *
      * @param t
      */
    def notDelayed(t: Long): Unit = {
      println("In delayed method")
      println("Param: " + t)
    }

    /**
      * By-Name参数形式，即参数定义的时候使用t: =>的形式，懒加载，按需调用
      * @param t
      */
    def delayed(t: =>Long): Unit = {
      println("In delayed method")
      println("Param: " + t)
    }
    notDelayed(time())
    println
    delayed(time())
  }

}
