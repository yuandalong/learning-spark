package com.ydl.learning.scala.concurrency

/**
  * Option类型操作demo
  *
  * Created by ydl on 2018/4/16.
  */
object OptionDemo {
  def main(args: Array[String]): Unit = {
    //    Option类型的值通常作为Scala集合类型（List,Map等）操作的返回类型。比如Map的get方法
    val capitals = Map("France" -> "Paris", "Japan" -> "Tokyo", "China" -> "Beijing")
    //    Some(Paris)
    println(capitals.get("France"))
    //    None
    println(capitals.get("North Pole"))
    val france = capitals.get("France")
    //Option类型取值demo
    //1 注意key不存在时会报java.util.NoSuchElementException: None.get异常，所以建议用方法2
    println(france.get)
    //2 ,带默认值
    println(france.getOrElse(""))
    //3 类型匹配
    france match {
      case Some(s) => println(s)
      case None => println("null")
    }

  }
}
