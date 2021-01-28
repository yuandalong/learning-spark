package com.ydl.learning.scala.async


import scala.concurrent._
import scala.concurrent.duration._

/**
  *
  *
  * @author ydl
  * @since 2021/1/25
  */
object AsyncHappy extends App {

  import ExecutionContext.Implicits.global

  // task definitions
  //以Future[Int]的形式创建事件
  def task1(input: Int) = TimedEvent.delayedSuccess(2, input + 1)

  def task2(input: Int) = TimedEvent.delayedSuccess(5, input + 2)

  def task3(input: Int) = TimedEvent.delayedSuccess(3, input + 3)

  def task4(input: Int) = TimedEvent.delayedSuccess(1, input + 4)

  /** Run tasks with block waits. */
  /**
    * 阻塞等待任务
    * 主线程会等待每个任务完成
    * 其从v2和v3会并行执行
    *
    * @return
    */
  def runBlocking() = {
    //Duration.Inf 无限期等待，直到返回结果
    val v1 = Await.result(task1(1), Duration.Inf)
    val future2 = task2(v1)
    val future3 = task3(v1)
    val v2 = Await.result(future2, Duration.Inf)
    val v3 = Await.result(future3, Duration.Inf)
    val v4 = Await.result(task4(v2 + v3), Duration.Inf)
    //Promise的使用，先定义，后success或者failure，最后future
    //通常，完成Promise和完成Future的操作不会发生在同一个线程上。
    //更多的场景是你生成了Promise并且在另一个线程开始进行结果的计算，立刻返回尚未完成的Future给调用者。
    val result = Promise[Int]
    result.success(v4)
    result.future
  }

  /**
    * 使用回调的方式处理
    * onSuccess注册回调函数
    *
    * @return
    */
  def runOnSuccess() = {
    val result = Promise[Int]
    task1(1).onSuccess {
      case v1 =>
        val a = task2(v1)
        val b = task3(v1)
        a.onSuccess {
          case v2 =>
            b.onSuccess {
              case v3 => task4(v2 + v3).onSuccess {
                case x => result.success(x)
              }
            }
        }
    }
    result.future
  }

  /** Run tasks with flatMap. */
  def runFlatMap() = {
    task1(1) flatMap { v1 =>
      val a = task2(v1)
      val b = task3(v1)
      a flatMap { v2 =>
        b flatMap { v3 => task4(v2 + v3) }
      }
    }
  }

  def timeComplete(f: () => Future[Int], name: String) {
    println("Starting " + name)
    val start = System.currentTimeMillis
    val result = Await.result(f(), Duration.Inf)
    val time = System.currentTimeMillis - start
    println(name + " returned " + result + " in " + time + " ms.")
  }

  timeComplete(runBlocking, "runBlocking")
  timeComplete(runOnSuccess, "runOnSuccess")
  timeComplete(runFlatMap, "runFlatMap")

  // force everything to terminate
  System.exit(0)
}
