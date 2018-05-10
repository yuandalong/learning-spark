package com.ydl.learning.scala

import scala.collection.mutable.ArrayBuffer

/**
  * 模式匹配demo
  * Created by ydl on 2017/3/14.
  */
object CaseDemo extends App {
  var list = new ArrayBuffer[Int]()
  var x = 0
  for (i <- 1 to 100) {
    i match {
      //后面可以跟表达式 case后面不需要跟break 只要匹配上一个case 后面的case都不会执行
      //如12满足4和3整除，但只会执行匹配4的
      case 10 => x = 10
      case 50 => println(50)
      case 80 => println(80) //增加守卫条件
      case _ if (i % 4 == 0) => list.append(i)
      case _ if (i % 3 == 0) => println(i + ":能被3整除")
      case _ =>
    }
  }
  println(x)
  println(list.toString())
}

//定义case包，因为OopDemo中已经定义了Person
package Case {

  //Case Class一般被翻译成样例类，它是一种特殊的类，能够被优化以用于模式匹配，下面的代码定义了一个样例类：
  //抽象类Person
  abstract class Person

  //  当一个类被声名为case class的时候，scala会帮助我们做下面几件事情：
  //  1 构造器中的参数如果不被声明为var的话，它默认的话是val类型的，但一般不推荐将构造器中的参数声明为var
  //  2 自动创建伴生对象，同时在里面给我们实现子apply方法，使得我们在使用的时候可以不直接显示地new对象
  //  3 伴生对象中同样会帮我们实现unapply方法，从而可以将case class应用于模式匹配，关于unapply方法我们在后面的“提取器”那一节会重点讲解
  //  4 实现自己的toString、hashCode、copy、equals方法
  //  除此之此，case class与其它普通的scala类没有区别
  //case class Student
  case class Student(name: String, age: Int, studentNo: Int) extends Person

  //case class Teacher
  case class Teacher(name: String, age: Int, teacherNo: Int) extends Person

  //case class Nobody
  case class Nobody(name: String) extends Person

  object CaseClassDemo {
    def main(args: Array[String]): Unit = {
      //case class 会自动生成apply方法，从而省去new操作
      val p: Person = Student("john", 18, 1024) //match case 匹配语法
      p match {
        case Student(name, age, studentNo) => println(name + ":" + age + ":" + studentNo)
        case Teacher(name, age, teacherNo) => println(name + ":" + age + ":" + teacherNo)
        case Nobody(name) => println(name)
      }
    }
  }

  /**
    * scala 2.10.*之前的版本case class最多只能有22个参数，2.11之后没有限制
    */
  case class MaxParamTest(a1: String, a2: String, a3: String, a4: String, a5: String, a6: String, a7: String,
                          a8: String, a9: String, a10: String, a11: String, a12: String, a13: String, a14: String, a15: String,
                          a16: String, a17: String, a18: String, a19: String, a20: String, a21: String, a22: String, a23: String,
                          a24: String)

}

