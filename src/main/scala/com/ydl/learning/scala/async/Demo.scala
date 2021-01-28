package com.ydl.learning.scala.async

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.serializer.SerializeFilter

//异步必须加的隐式转换
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Future, Promise, _}
import scala.util.{Failure, Success}

/**
  *
  *
  * @author ydl
  * @since 2021/1/25
  */
object Demo extends App {
  val pojo = new Pojo

  /**
    * 定义一个成功的future
    *
    * @param input
    * @return
    */
  def succFuture(input: Pojo): Future[Pojo] = {
    println("succFuture:" + System.currentTimeMillis())
    //定义一个promise
    val result = Promise[Pojo]
    //使用Future代码块定义一个异步线程
    //也可以使用Thread定义一个线程
    Future {
      Thread.sleep(2000)
      input.setName("ydl")
      //返回成功
      result.success(input)
    }
    //返回future
    result.future
  }

  /**
    * 定义一个失败的future
    *
    * @param input
    * @return
    */
  def failFuture(input: Pojo): Future[Pojo] = {
    println("failFuture:" + System.currentTimeMillis())
    val result = Promise[Pojo]
    Future {
      try {
        Thread.sleep(3000)
        input.setAge(37)
        throw new Exception("fail")
      } catch {
        case e: Throwable =>
          //返回失败
          result.failure(e)
      }
      //返回失败
      result.failure(new Exception("fail"))
    }
    result.future

  }

  //使用Future的apply方法定义一个future，适用于不需要传参的情况
  val fut1 = Future {
    Thread.sleep(3000)
    pojo.setAge(37)
    2
  }

  val start = System.currentTimeMillis()
  val f1 = succFuture(pojo)
  val f2 = failFuture(pojo)

  //定义成功的回调
  f1 onSuccess {
    case x =>
      println("f1 " + x.getName)
  }
  f2 onFailure {
    case x =>
      println("f2 " + x.getMessage)
  }

  //除了具体的onSuccess和onFailure，还可以用onComplete，然后里面匹配成功或者失败
  f2 onComplete {
  case Success(x1) => println("f1 success:" + x1.getName)
  case Failure(e) => print(e.getMessage)
}

  //主线程阻塞，等待异步线程执行完，Duration.Inf是指直到执行完，也可以自行设置执行的超时时间
  Await.result(f1, Duration.Inf)
  Await.result(f2, Duration.Inf)

  println(JSON.toJSONString(pojo, new Array[SerializeFilter](0)))
  println(System.currentTimeMillis() - start)
}
