package com.ydl.learning.scala

/**
  * 集合操作demo
  * Created by ydl on 2017/3/20.
  */
object SetDemo extends App {
  //定义一个集合
  //这里使用的是mutable
  val numsSet = Set(3.0, 5)
  //numsSet: scala.collection.mutable.Set[Double] = Set(5.0, 3.0)

  //向集中添加一个元素，同前一讲中的列表和数组不一样的是
  //，Set在插入元素时并不保元素的顺序
  //默认情况下，Set的实现方式是HashSet实现方式，
  //集中的元素通过HashCode值进行组织
  numsSet + 6
  println(numsSet)
  println(numsSet + 6)
  //res20: scala.collection.mutable.Set[Double] = Set(5.0, 6.0, 3.0)

  //遍历集
  for (i <- numsSet) println(i)
  //  5.0
  //  6.0
  //  3.0

  //如果对插入的顺序有着严格的要求，则采用scala.collection.mutalbe.LinkedHashSet来实现
  val linkedHashSet = scala.collection.mutable.LinkedHashSet(3.0, 5)
  // linkedHashSet: scala.collection.mutable.LinkedHashSet[Double] = Set(3.0, 5.0)

  linkedHashSet + 6
  //res26: scala.collection.mutable.LinkedHashSet[Double] = Set(3.0, 5.0, 6.0)

  //交集
  // &方法等同于interset方法
  Set(1, 2, 3) & Set(2, 4)
  Set(1, 2, 3) intersect Set(2, 4)

  //并集
  Set(1, 2, 3) ++ Set(2, 4)
  // |方法等同于union方法
  Set(1, 2, 3) | Set(2, 4)
  Set(1, 2, 3) union Set(2, 4)

  //差集
  //得到 Set(1,3)
  Set(1, 2, 3) -- Set(2, 4)
  Set(1, 2, 3) &~ Set(2, 4)
  Set(1, 2, 3) diff Set(2, 4)

  println(Set(1, 2, 3) & Set(2, 4))
  println(Set(1, 2, 3) intersect Set(2, 4))
}
