package com.ydl.learning.spark.transformationsDemo

/**
  * aggregate算子demo，seqOp相当于map操作，comb相当于reduce，zeroValue是每个partition的初始值
  * aggregate[U: ClassTag](zeroValue: U)(seqOp: (U, T) => U, combOp: (U, U) => U)
  *
  * @author ydl
  * @since 2019/2/20
  */
object AggregateDemo extends BaseSc with App {
  var data = List(2, 5, 8, 1, 2, 6, 9, 4, 3, 5)
  var res = sc.parallelize(data, 2).aggregate((0, 0))(
    // seqOp
    (acc, number) => (acc._1 + number, acc._2 + 1),
    // combOp
    (par1, par2) => (par1._1 + par2._1, par1._2 + par2._2)
  )

  println(res)

  sc.stop
}
