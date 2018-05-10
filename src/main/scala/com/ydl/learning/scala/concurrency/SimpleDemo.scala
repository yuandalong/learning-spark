package com.ydl.learning.scala.concurrency

import java.net.ServerSocket

/**
  * 简单多线程示例
  *
  * Created by ydl on 2018/2/26.
  */
class SimpleDemo(port: Int, poolSize: Int) extends Runnable {
  val serverSocket = new ServerSocket(port)

  def run() {
    while (true) {
      // This will block until a connection comes in.
      val socket = serverSocket.accept()
      //这段代码的主要缺点是在同一时间，只有一个请求可以被相应
      //(new Handler(socket)).run()
      //你可以把每个请求放入一个线程中处理。只要简单改变
      (new Thread(new Handler(socket))).start()

    }
  }
}


object SimpleDemo {
  def main(args: Array[String]): Unit = {
    (new SimpleDemo(2020, 2)).run
  }
}