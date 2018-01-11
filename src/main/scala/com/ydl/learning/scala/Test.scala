package com.ydl.learning.scala

import java.io.FileNotFoundException

/**
  * Test
  *
  * Created by ydl on 2017/12/8.
  */
class Test(d: String, e: Int) {
    //(d: String, e: Int)就是主构造函数
    private[this] var f = "123"
    private var a = "a"
    val b = "123"
    var c = "456"
    println(f)

    def this(g: String) {
        this("1", 2)
    }
}

object Test {
    println("123")
    val t = 1

    def getT = t

    def test(a: String) {
        println(a)
    }

    val a = test(_)
    val array = Array(1, 2)
    val biBao = (i: Int) => i * t

    def main(args: Array[String]): Unit = {

    }
    @throws(classOf[FileNotFoundException])
    def data(array: Array[String]): Unit = {
        array match {
            case Array("spark") => println("spark")
            //直接定义变量
            case Array(a, b, c) => println(a + "|" + b + "|" + c)
            //匹配数据的前几个元素
            case Array("scala", _*) => println("scala ...")
        }
    }
}
