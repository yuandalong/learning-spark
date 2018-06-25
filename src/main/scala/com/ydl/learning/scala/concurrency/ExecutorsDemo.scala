package com.ydl.learning.scala.concurrency

import java.net.ServerSocket
import java.util.concurrent.{ExecutorService, Executors}

/**
  * 线程池
  *
  * Created by ydl on 2018/2/27.
  */
class ExecutorsDemo(port: Int, poolSize: Int) extends Runnable {
  val serverSocket = new ServerSocket(port)
  val pool: ExecutorService = Executors.newFixedThreadPool(poolSize)

  override def run(): Unit = {
    try {
      while (true) {
        // This will block until a connection comes in.
        val socket = serverSocket.accept()
        pool.execute(new Handler(socket))
      }
    } finally {
      pool.shutdown()
    }
  }
}

object ExecutorsDemo {
  def main(args: Array[String]): Unit = {
    //获取cpu核心数
    println(Runtime.getRuntime().availableProcessors())
    new ExecutorsDemo(2020, 16).run()
  }
}

