package com.ydl.learning.scala.async

import java.util.{Timer, TimerTask}

import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * 定时事件代码
  *
  * 使用java.util.Timer安排java.util.TimerTask以在延迟后执行。 每个TimerTask在运行时都会完成一个关联的TimerTask 。
  * delayedSuccess函数调度任务以在其运行时成功完成Scala Future[T] ，并将未来返回给调用方。
  * delayedFailure函数返回相同的future类型，但使用的任务将以IllegalArgumentException故障完成future
  *
  * @author ydl
  * @since 2021/1/25
  */
object TimedEvent {
  val timer = new Timer

  /** Return a Future which completes successfully with the supplied value after secs seconds. */
  def delayedSuccess[T](secs: Int, value: T): Future[T] = {
    println("secs:%d ,value:%s, currentTimeMillis:%d".format(secs, value, System.currentTimeMillis()))
    val result = Promise[T]
    //异步执行的代码
    //注意此处必须是异步执行的代码块，否则会导致外部的异步调用也变成顺序的同步执行
    //普通代码可以用Future代码块包括起来实现异步，如
    //Future{
    //  Thread.sleep(1000)
    //}
    timer.schedule(new TimerTask() {
      def run(): Unit = {
        result.success(value)
      }
    }, secs * 1000)
    result.future
  }

  /** Return a Future which completes failing with an IllegalArgumentException after secs
    * seconds. */
  def delayedFailure(secs: Int, msg: String): Future[Int] = {
    val result = Promise[Int]
    timer.schedule(new TimerTask() {
      def run(): Unit = {
        result.failure(new IllegalArgumentException(msg))
      }
    }, secs * 1000)
    result.future
  }
}
