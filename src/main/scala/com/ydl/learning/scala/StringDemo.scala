package com.ydl.learning.scala

/**
  * Created by ydl on 2017/3/15.
  */
object StringDemo extends App {
  val name = "ydl"
  val age = 30
  //字符串格式化
  println("%s is %d".format(name, age))
  println(s"$name is $age")
  println(name.equals("ydl"))
  if (name.equals("ydl"))
    println("adfadf")
  //三引号的使用
  var stringSample =
    """Welcome to

                                       Scala, "Hello World" """
  println(stringSample)
  //三引号对齐，行头使用|，然后用stripMargin方法
  stringSample =
    """   |Welcome to
      |Scala, "Hello World" """.stripMargin
  println(stringSample)
  //需转义的特殊字符可使用单引号
  println('"')
  //scala中\的转义的特殊处理
  //原因是：'\' 其实在正则表达式中依然是转移字符，虽然 """\""" 这种写法，在Scala中不需要转义就代表 '\' 字符，
  //但是java.util.regex.Pattern中仍然将其视为转义符，而转义符后面没有跟待转义的字符，然后就报错了。
  //所以，转义符'\' 后再加上 '\' ，再进行一次转义才相当于字符 '\' 。
  val str =
  """\哈哈哈\"""
  println(str.replaceAll("""\\""", ""))
  println(str.replaceAll("\\\\", ""))
}
