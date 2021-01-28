package com.ydl.learning.scala

/**
  * 特质
  * 类似java的接口
  * 与接口不同的是，它还可以定义属性和方法的实现。
  * 类使用extends关键字继承trait，要继承多个trait时可以使用extends A with B的语法
  *
  * @author ydl
  * @since 2020/9/26
  */
trait TraitDemo {
  val a = ""

  def b(c: String): Unit = {
    print(c)
  }

}

class TraitTest extends TraitDemo{
  def test: Unit ={
    b("aaa")
  }
}
