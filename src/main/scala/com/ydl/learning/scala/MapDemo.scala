package com.ydl.learning.scala

/**
  * Map demo
  * Map和Tuple的区别是Map是键值对的集合，元组则是不同类型值的聚集
  * Created by ydl on 2017/3/20.
  */
object MapDemo extends App {
    //直接初始化
    // ->操作符，左边是key,右边是value
    val studentInfo = Map("john" -> 21, "stephen" -> 22, "lucy" -> 20)
    //immutable不可变，它不具有以下操作
    //studentInfo.clear()


    //创建可变的Map
    val studentInfoMutable = scala.collection.mutable.Map("john" -> 21, "stephen" -> 22, "lucy" -> 20)
    //mutable Map可变，比如可以将其内容清空
    studentInfoMutable.clear()

    //遍历操作1
    for (i <- studentInfoMutable) println(i)

    //遍历操作2
    studentInfoMutable.foreach(e => {
        val (k, v) = e;
        println(k + ":" + v)
    }
    )

    //遍历操作3
    studentInfoMutable.foreach(e => println(e._1 + ":" + e._2))

    //定义一个空的Map
    val xMap = new scala.collection.mutable.HashMap[String, Int]()

    //往里面填充值
    xMap.put("spark", 1)

    //-> 初始化Map，也可以通过 ("spark",1)这种方式实现(元组的形式）
    val xMap2 = scala.collection.mutable.Map(("spark", 1), ("hive", 1))

    //获取元素
    println(xMap.get("spark"))
    //Option[Int] = Some(1)

    println(xMap.get("SparkSQL"))

    //Option[Int] = None

    //  Option,None,Some类型
    //  Option、None、Some是scala中定义的类型，它们在scala语言中十分常用，因此这三个类型非学重要。
    //  None、Some是Option的子类，它主要解决值为null的问题，在Java语言中，对于定义好的HashMap，如果get方法中传入的键不存在，方法会返回
    // null，在编写代码的时候对于null的这种情况通常需要特殊处理，然而在实际中经常会忘记，因此它很容易引起 NullPointerException异常。
    // 在Scala语言中通过Option、None、Some这三个类来避免这样的问题，这样做有几个好处，首先是代码可读性更强，当看到Option时，我们自然而然
    // 就知道它的值是可选的，然后变量是Option，比如Option[String]的时候，直接使用String的话，编译直接通不过。
    //
    //  前面我们看到：
    //
    //  scala> xMap.get("spark")
    //  res19: Option[Int] = Some(1)
    //  那要怎么才能获取到最终的结果呢，

    //通过模式匹配得到最终的结果
    def show(x: Option[Int]) = x match {
        case Some(s) => s
        case None => "????"
    }

    println(show(xMap.get("spark")))
    println(show(xMap.get("sparkSQL")))
    //map取值,如果可以不存在会报错java.util.NoSuchElementException: None.get
    println(xMap.get("spark").get)
    //所有用get的正确方式是先进行isDefined判断
    if (xMap.get("spark2").isDefined) {
        println(xMap.get("spark2"))
    } else {
        println("is not defined")
    }
    //建议使用getOrElse方法设置默认值来替换isDefinde和get方法的组合
    println(xMap.get("spark2").getOrElse("is not defined"))
}
