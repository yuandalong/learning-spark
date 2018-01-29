package com.ydl.learning.scala

/**
  * 元组demo
  * Map和Tuple的区别是Map是键值对的集合，元组则是不同类型值的聚集
  * Created by ydl on 2017/3/20.
  */
object TupleDemo extends App {
    //元组的定义
    ("hello", "china", "beijing")
    //res23: (String, String, String) = (hello,china,beijing)

    ("hello", "china", 1)
    //res24: (String, String, Int) = (hello,china,1)

    var tuple = ("Hello", "China", 1)
    //tuple: (String, String, Int) = (Hello,China,1)

    //访问元组内容，_表示元组下班，从1开始
    tuple._1
    //res25: String = Hello

    tuple._2
    //res26: String = China

    tuple._3
    //res27: Int = 1

    //通过模式匹配获取元组内容
    val (first, second, third) = tuple
    println(first)
    println(second)
    println(third)
    //元组可以很好得与模式匹配相结合。
    val t = ("java", 1)
    t match {
        case ("java", count) => {
            println(count + " is java")
        }
        case (lang, count) => {
            println(lang + "" + count)
        }
    }

    //在创建两个元素的元组时，可以使用特殊语法：->
    val a = 1 -> 2
    println(a._1)

    val t1=("1","2")
    val t2=("1","2")
    println(t1==t2)
}
