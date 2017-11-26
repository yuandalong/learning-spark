package com.ydl.learning.scala

/**
  * Created by ydl on 2017/3/15.
  */
object StringDemo extends App{
  val name = "ydl"
  val age = 30
  //字符串格式化
  println("%s is %d".format(name,age))
  println(s"$name is $age")
  println(name.equals("ydl"))
  if(name.equals("ydl"))
    println("adfadf")
  //三引号的使用
  var stringSample = """Welcome to

                                       Scala, "Hello World" """
  println(stringSample)
  //三引号对齐，行头使用|，然后用stripMargin方法
  stringSample ="""   |Welcome to
        |Scala, "Hello World" """.stripMargin
  println(stringSample)
  //需转义的特殊字符可使用单引号
  println('"')
}
