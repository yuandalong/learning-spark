package com.ydl.learning.scala

/**
  * Scala中Method方法和Function函数的区别
  *
  * @author ydl
  * @since 2018/6/22
  */
object MethodAndFunDiff {
  def main(args: Array[String]): Unit = {
    //在Scala中方法不是值，而函数是。所以一个方法不能赋值给一个val变量，而函数可以
    def increment(n: Int): Int = n + 1

    //将这个方法赋值给变量fun失败，因为方法def不能赋值给变量val
    //val fun = increment
    //可以通过将方法转化为函数的方式实现
    val fun = increment _
    println(fun)
    println(fun(1))

    val list = List(1, 2, 3)
    //有时候根据上下文编译器可以自动将方法转换为函数，而不需要使用下划线
    list.map(increment _).foreach(println _)
    list.map(increment).foreach(println)

    //将方法转换为函数的时候，如果方法有重载的情况，必须指定参数和返回值的类型
    val fun1 = Tool.increment: Int => Int
    val fun2 = Tool.increment: (Int, Int) => Int
    list.map(fun1).foreach(println)
    list.map(fun2(_,1)).foreach(println)

    //对于一个无参数的方法是没有参数列表的
    def x = println("hi")
    //而对于函数是有一个空参数列表
    val y = x _
    //调用y函数时如果不加参数列表()，不会报错，但会打印出函数的对象信息，而不是调用还是
    y()

    //多个参数的方法可以转换为多元函数
    def plus(x: Int, y: Int): Int = x + y
    val p = plus _
    println(p(1,2))

    //多个参数的方法变成柯里函数
    def plus2(x: Int)(y: Int): Int = x + y
    //plus方法只能同时传入两个参数才能执行，分步执行会报错
    plus2(1)(2)
    //编译不通过，会报missing argument list
    //plus2(1)
    //如果要分步执行，必须转化为函数
    val p2 = plus2(1) _
    println(p2(2))

    //在Scala中的值是不能有类型的，所以如果将带有类型的方法转换为函数，必须指定具体的参数类型
    def id[T] (x : T): T = x
    //没有指定T的具体类型，编译会报type mismatch
    //val fun3 = id _
    //要转化为函数必须指定T的具体类型
    val fun3 = id[Int] _
    println(fun3(1))

    //By-Name参数,必须是函数才能作为test的返回值，所以最后的_不能去掉，去掉之后就不是把方法x转换成函数
    def test(x: =>Unit): ()=>Unit = x _
    val t = test(println("aaaa"))
    t()

    //对于一个方法，可以使用参数序列，但是如果转换成函数之后，要使用Seq对象
    def exec(as:Int*) :Int = as.sum
    exec(1,2,3)
    val f = exec _
    //编译报错too many arguments for method
    //f(1,2,3)
    //必须使用Seq对象
    f(Seq(1,2,3))

    //方法是支持参数默认值的用法，但是函数会忽略参数默认值的，所以函数不能省略参数
    def exec2(s: String, n: Int = 2) = s * n
    exec2("HI")
    val fun4 = exec2 _
    //必须设置所有参数
    fun4("HI",1)
  }
}

/**
  * 方法重载后调用方法demo
  */
object Tool {
  def increment(n: Int): Int = n + 1

  def increment(n: Int, step: Int): Int = n + step
}