package com.ydl.learning.scala.concurrency

import java.net.Socket

/**
  * 线程处理demo，其他类调用该类进行多线程处理
  *
  * Created by ydl on 2018/2/27.
  */
class Handler(socket: Socket) extends Runnable {
    //当前线程名称
    def message = (Thread.currentThread.getName() + "\n")

    def run() {
        println(message)
        socket.getOutputStream.write(message.getBytes)
        Thread.sleep(10000)
        socket.getOutputStream.close()
    }
}
