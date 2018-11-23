package com.ydl.learning.scala.concurrency

/**
  * Scala并发是建立在Java并发模型基础上的。
  *
  * 在Sun JVM上，对IO密集的任务，我们可以在一台机器运行成千上万个线程。
  *
  * 一个线程需要一个Runnable。你必须调用线程的 start 方法来运行Runnable。
  *
  * Created by ydl on 2018/2/26.
  */
object ThreadDemo {
  def main(args: Array[String]): Unit = {
    val hello = new Thread(new Runnable {
      override def run(): Unit = {
        println("hello world")
        Thread.sleep(10000)
        println("thread end")
      }
    })
    hello.start()
    hello.join()
    println("main end")
  }
}
