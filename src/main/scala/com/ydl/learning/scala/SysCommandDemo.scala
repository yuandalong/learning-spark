package com.ydl.learning.scala

import scala.sys.process._

/**
  * 执行系统命令demo
  * 引入scala.sys.process._，字符串后面加！就可以执行系统命令
  *
  * @author ydl 
  */
object SysCommandDemo extends App {
  "pwd" !
}
