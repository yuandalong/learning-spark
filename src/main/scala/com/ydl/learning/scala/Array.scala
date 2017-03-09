package com.ydl.learning.scala

object Array {
  def main(args: Array[String]): Unit = {
    val array = new Array[String](3)
    array(0) = "hello"
    array(1) = ", "
    array(2) = "world" 
    array.foreach { print }
  }
}