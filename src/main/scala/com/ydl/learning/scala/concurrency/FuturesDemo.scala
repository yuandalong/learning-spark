package com.ydl.learning.scala.concurrency

import java.util.concurrent.{Callable, ExecutorService, Executors, FutureTask}

/**
  * FuturesDemo
  * execute和submit区别
  * 1、接收的参数不一样
  * 2、submit有返回值，而execute没有
  * 3、submit方便Exception处理
  *
  * Created by ydl on 2018/2/27.
  */
object FuturesDemo {
  val future = new FutureTask[String](new Callable[String]() {
    def call(): String = {
      Thread.sleep(2000)
      throw new Exception("test")
      "adfadf"
    }
  })

  def main(args: Array[String]): Unit = {
    val threadPool:ExecutorService=Executors.newFixedThreadPool(3)
    threadPool.execute(future)
    try {
      println(threadPool.submit(future).get())
      println(future.get())
    } catch {
      case e:Throwable =>println(e)
    }
    println("end")
    threadPool.shutdown()
  }
}
