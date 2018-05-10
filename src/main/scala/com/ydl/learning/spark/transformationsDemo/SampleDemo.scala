package com.ydl.learning.spark.transformationsDemo

/**
  * 这个方法可以用于对数据进行采样，比如从1000个数据里面随机5个数据。
  * *
  * 第一个参数withReplacement代表是否进行替换，如果选true，上面的例子中，会出现重复的数据
  * 第二个参数fraction 表示随机的比例
  * 第三个参数seed 表示随机的种子
  * Created by ydl on 2017/3/27.
  */
object SampleDemo extends BaseSc with App {
  //多个父类
  //创建数据
  val data = sc.parallelize(1 to 1000, 1)

  //采用固定的种子seed随机
  data.sample(false, 0.005, 0).collect.foreach(println)
  //输出为 Array[Int] = Array(53, 423, 433, 523, 956, 990)
  println("-----------------")
  //采用随机种子
  data.sample(false, 0.005, scala.util.Random.nextInt(1000)).collect.foreach(println)
  //输出为 Array[Int] = Array(136, 158)
}
