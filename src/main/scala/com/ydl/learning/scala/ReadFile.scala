package com.ydl.learning.spark

import scala.io.Source

/**
  * 读取文件
  */
object ReadFile {
  def widthOfLength(s: String) = s.length.toString.length

  def main(args: Array[String]): Unit = {

    if (args.length > 0) {
      val lines = Source.fromFile(args(0)).getLines.toList //读取文件的每一行到list
      val longestLine = lines.reduceLeft( //对list从左到右进行reduce操作，两两元素比较长度，遍历出最大长度
        (a, b) => if (a.length > b.length) a else b)
      val maxWidth = widthOfLength(longestLine)
      for (line <- lines) {
        val numSpaces = maxWidth - widthOfLength(line)
        val padding = " " * numSpaces //文本对齐
        print(padding + line.length + " | " + line)
      }
    } else
      Console.err.println("Please enter filename")
  }
}