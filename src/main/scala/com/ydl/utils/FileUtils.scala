package com.ydl.utils

import java.io.{File, PrintWriter}

import scala.io.Source

/**
  * Created by ydl on 2017/3/24.
  */
object FileUtils {
  /**
    * 递归获取目录名称
    *
    * @param dir
    * @return
    */
  def subdirs(dir: File): Iterator[File] = {
    val children = dir.listFiles.filter(_.isDirectory)
    children.toIterator ++ children.toIterator.flatMap(subdirs _)
  }

  /**
    * 获取文件名
    *
    * @param dir
    * @return
    */
  def subdirs2(dir: File): Iterator[File] = {
    val d = dir.listFiles.filter(_.isDirectory)
    val f = dir.listFiles.filter(_.isFile).toIterator
    f ++ d.toIterator.flatMap(subdirs2 _)
  }

  /**
    * 获得指定目录下所有的文件名和目录名
    *
    * @param dir
    * @return
    */
  def subdirs3(dir: File): Iterator[File] = {
    val d = dir.listFiles.filter(_.isDirectory)
    val f = dir.listFiles.toIterator
    f ++ d.toIterator.flatMap(subdirs3 _)
  }

  /**
    * 读取文件内容
    *
    * @param path
    * @return
    */
  def readFile2List(path: String): List[String] = {
    Source.fromFile(path).getLines.toList
  }

  /**
    * 写文件
    *
    * @param list
    * @param path
    */
  def wirteList2File(list: List[String], path: String): Unit = {
    val writer = new PrintWriter(new File(path))
    list.foreach(
      writer.println(_)
    )
    writer.close()
  }

  def main(args: Array[String]): Unit = {
    val file = new File("""/Volumes/ydl-disk-a/技术/spark/IMF Spark绝密视频泄密版""")
    //    val newFile = new File("""/Users/ydl/Downloads/IMFSpark""")
    //    file.renameTo(newFile)
    subdirs2(file).foreach(child => {
      val path = child.getPath
      //重命名
      if (path.indexOf("王家林DT大数据梦工厂大数据IMF传奇行动绝密视频（泄密版）") > 0) {
        val newPath = path.replace("王家林DT大数据梦工厂大数据IMF传奇行动绝密视频（泄密版）", "")
        new File(path).renameTo(new File(newPath))
      }

      //      println(child.getPath)
    })

  }
}
