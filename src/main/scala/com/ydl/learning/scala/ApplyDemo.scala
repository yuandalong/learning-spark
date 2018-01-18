package com.ydl.learning.scala

/**
  * apply 方法
  * 当类或对象有一个主要用途的时候，apply方法为你提供了一个很好的语法糖。
  *
  * Created by ydl on 2017/11/26.
  */
class ApplyDemo {
    def apply() = 0
}

object ApplyDemo extends App {
    val a = new ApplyDemo
    //在这里，我们实例化对象看起来像是在调用一个方法
    println(a())
    //调用伴生对象的apply方法，可以不用new来创建实例，伴生对象通常作为工厂使用
    val bar = Bar("123")
}

//单例对象可以和类具有相同的名称，此时该对象也被称为“伴生对象”。我们通常将伴生对象作为工厂使用。
//下面是一个简单的例子，可以不需要使用’new’来创建一个实例了。
class Bar(foo: String)

object Bar {
    def apply(foo: String) = {
        println(foo)
        new Bar(foo)
    }
}
