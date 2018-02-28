package com.ydl.learning.scala.concurrency

import java.util.concurrent.{Callable, Executor, FutureTask}

/**
  * FuturesDemo
  *
  * Created by ydl on 2018/2/27.
  */
object FuturesDemo {
    val future = new FutureTask[String](new Callable[String]() {
        def call(): String = {
            Thread.sleep(10000)
            "adfadf"
        }})
    //TODO 有时间在研究怎么调用Future http://twitter.github.io/scala_school/zh_cn/concurrency.html
    def main(args: Array[String]): Unit = {
        println(future.get())
    }
}
