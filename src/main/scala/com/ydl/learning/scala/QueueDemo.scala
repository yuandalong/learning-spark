package com.ydl.learning.scala

/**
  * 队列操作 先进先出
  * Created by ydl on 2017/3/20.
  */
object QueueDemo extends App {
  //immutable queue
  var queue = scala.collection.immutable.Queue(1, 2, 3)
  // queue: scala.collection.immutable.Queue[Int] = Queue(1, 2, 3)

  //出队
  queue.dequeue
  // res38: (Int, scala.collection.immutable.Queue[Int]) = (1,Queue(2, 3))

  //入队
  queue.enqueue(4)
  //res40: scala.collection.immutable.Queue[Int] = Queue(1, 2, 3, 4)

  //mutable queue
  var queue2 = scala.collection.mutable.Queue(1, 2, 3, 4, 5)
  //queue: scala.collection.mutable.Queue[Int] = Queue(1, 2, 3, 4, 5)

  //入队操作
  queue2 += 5
  // res43: scala.collection.mutable.Queue[Int] = Queue(1, 2, 3, 4, 5, 5)

  //集合方式
  queue2 ++= List(6, 7, 8)
  //res45: scala.collection.mutable.Queue[Int] = Queue(1, 2, 3, 4, 5, 5, 6, 7, 8)
}
