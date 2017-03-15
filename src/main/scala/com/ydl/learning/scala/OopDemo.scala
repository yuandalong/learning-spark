package com.ydl.learning.scala

/**
  * 面向对象demo
  * Created by ydl on 2017/3/10.
  */
object OopDemo {
  //伴生对象
  def main(args: Array[String]): Unit = {
    val demo = new OopDemo
    demo.storeId = "test"
    println(demo.storeId)
  }
}


class OopDemo {
  //伴生类
  /**
    * 属性
    */
  private[this] var _storeId: String = null

  /**
    * 相当于get方法， oopDemoObject.storeId调用
    *
    * @return
    */
  def storeId: String = _storeId //没加修饰符的方法、属性在scala中默认是public的

  /**
    * 相当于set方法  oopDemoObject.storeId = "asdfadf"调用
    *
    * @param value
    */
  def storeId_=(value: String): Unit = {
    _storeId = value
  }

  //当在创建对象时，需要进行相关初始化操作时，可以将初始化语句放在类体中，同样也可以在类中添加或重写相关方法
  //主构建器还可以使用默认参数
  class Person(val name: String = "", val age: Int = 18) {
    //println将作为主构建器中的一部分，在创建对象时被执行
    println("constructing Person ........")
    private var sex: Int = 0

    //重写toString()方法
    override def toString() = name + ":" + age

    //辅助构造器 辅助构建器的名称为this
    def this(name: String, age: Int, sex: Int) {
      this(name, age) //调用辅助构造函数时，必须先调用主构造函数或其它已经定义好的构造函数
      this.sex = sex
    }

  }

  var p = new Person("john", 29)
  p = new Person //使用默认参数
  p = new Person("ydl")
  //p = new Person(20) 编译不通过，
  p = new Person(age=20)
}


//定义Student类，该类称为伴生类，因为在同一个源文件里面，我们还定义了object Student
class Student(var name: String, var age: Int) {
  private val sex: Int = 0

  //直接访问伴生对象的私有成员
  def printCompanionObject() = println(Student.studentNo)

}

//伴生对象
object Student {
  private var studentNo: Int = 0;

  def uniqueStudentNo() = {
    studentNo += 1
    studentNo
  }

  //定义自己的apply方法
  def apply(name: String, age: Int) = new Student(name, age)

  def main(args: Array[String]): Unit = {
    println(Student.uniqueStudentNo())
    val s = new Student("john", 29)
    //直接访问伴生类Student中的私有成员
    println(s.sex)

    //直接利用类名进行对象的创建，这种方式实际上是调用前面的apply方法进行实现，这种方式的好处是避免了自己手动new去创建对象
    //以下三种方法创建对象都是等效的
    val s1 = Student("john", 29)
    val s2 = Student.apply("john", 29)
    val s3 = new Student("john", 29)
    println(s1.name)
    println(s2.name)
    println(s3.name)
  }
}

//抽象类
abstract class Animal {
  //抽象字段(域）
  //前面我们提到，一般类中定义字段的话必须初始化，而抽象类中则没有这要求
  var height: Int

  //抽象方法
  def eat: Unit
}

//Person继承Animal，对eat方法进行了实现
//通过主构造器对height参数进行了初始化
class Person(var height: Int) extends Animal {
  //对父类中的方法进行实现，注意这里面可以不加override关键字
  def eat() = {
    println("eat by mouth")
  }

}

//通过扩展App创建程序的入口
object Person extends App {
  new Person(10).eat()
}

//包对象 包对象主要用于定义常量、工具函数，使用时直接通过包名引用
//利用package关键字定义单例对象
package object Math {
  val PI = 3.141529
  val THETA = 2.0
  val SIGMA = 1.9
}

class Coputation {
  def computeArea(r: Double) = Math.PI * r * r
}

//将java.util.HashMap重命名为JavaHashMap
import java.util.{HashMap => JavaHashMap}
//通过HashMap=> _，这样类便被隐藏起来了
//import java.util.{HashMap=> _,_}
import scala.collection.mutable.HashMap

object RenameUsage {
  def main(args: Array[String]): Unit = {
    val javaHashMap = new JavaHashMap[String, String]()
    javaHashMap.put("Spark", "excellent")
    javaHashMap.put("MapReduce", "good")
    for (key <- javaHashMap.keySet().toArray) {
      println(key + ":" + javaHashMap.get(key))
    }

    val scalaHashMap = new HashMap[String, String]
    scalaHashMap.put("Spark", "excellent")
    scalaHashMap.put("MapReduce", "good")
    scalaHashMap.foreach(e => {
      val (k, v) = e
      println(k + ":" + v)
    })
  }

}