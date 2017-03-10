package com.ydl.learning.scala.oop

/**
  * 面向对象demo
  * Created by ydl on 2017/3/10.
  */
object  OopDemo{//伴生对象
  def main(args: Array[String]): Unit = {
    val demo = new OopDemo
    demo.storeId = "test"
    println(demo.storeId)
  }
}


class OopDemo {//伴生类
  /**
    * 属性
    */
  private[this] var _storeId: String = null

  /**
    * 相当于get方法， oopDemoObject.storeId调用
    * @return
    */
  def storeId: String = _storeId//没加修饰符的方法、属性在scala中默认是public的

  /**
    * 相当于set方法  oopDemoObject.storeId = "asdfadf"调用
     * @param value
    */
  def storeId_=(value: String): Unit = {
    _storeId = value
  }


}
