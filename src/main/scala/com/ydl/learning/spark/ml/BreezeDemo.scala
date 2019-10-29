package com.ydl.learning.spark.ml

/**
  * 在 Scala 编程中，我们使用 Breeze 库来表示向量。
  *
  * @author ydl
  * @since 2019/3/21
  */

import breeze.linalg.DenseVector
import breeze.math.Complex
import breeze.linalg.SparseVector
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.linalg.Vector

object BreezeDemo {

  /**
    * 复数demo
    */
  def complexDemo(): Unit = {
    // 复数 = 实数部 + 虚数部 i
    val i = Complex.i
    println(i)
    // 加法
    println((1 + 2 * i) + (2 + 3 * i))
    // 减法
    println((1 + 2 * i) - (2 + 3 * i))
    // 除法
    println((5 + 10 * i) / (3 - 4 * i))
    // 乘法
    println((1 + 2 * i) * (-3 + 6 * i))
    println((1 + 5 * i) * (-3 + 2 * i))
    // 取反
    println(-(1 + 2 * i))
    // 多项加法
    val x = List((5 + 7 * i), (1 + 3 * i), (13 + 17 * i))
    println(x.sum)
    // 多项乘法
    val x1 = List((5 + 7 * i), (1 + 3 * i), (13 + 17 * i))
    println(x1.product)
    // 多项排序
    val x2 = List((5 + 7 * i), (1 + 3 * i), (13 + 17 * i))
    println(x2.sorted)
  }

  /**
    * 向量demo
    */
  def vectorDemo(): Unit = {
    // 密集向量
    // DenseVector 是对支持数值运算的数组的一种封装。
    // 下面先看下密集向量的计算。首先借 助 Breeze 创建一个密集向量对象，然后更新索引为 3 的元素的值。
    val v = DenseVector(2f, 0f, 3f, 2f, -1f)
    v.update(3, 6f)
    println(v)

    // 稀疏向量
    // SparseVector 表示多数元素为 0 且支持数值运算的向量，即稀疏向量。
    // 下面的代码先借助 Breeze 创建了一个稀疏向量，然后对其中的各值加 1：
    val sv: SparseVector[Double] = SparseVector(5)()
    sv(0) = 1
    sv(2) = 3
    sv(4) = 5
    val m: SparseVector[Double] = sv.mapActivePairs((_, x) => x + 1)
    println(m)
  }

  /**
    * spark自带向量包linalg使用
    */
  def vectorSparkDemo(): Unit = {
    val dVectorOne: Vector = Vectors.dense(1.0, 0.0, 2.0)
    println("dVectorOne:" + dVectorOne)
    // 稀疏向量(1.0, 0.0, 2.0, 3.0)对应非零条目
    val sVectorOne: Vector = Vectors.sparse(4, Array(0, 2, 3), Array(1.0, 2.0, 3.0))
    println(sVectorOne)
    // 创建一个稀疏向量(1.0, 0.0, 2.0, 2.0) 并指定其非零条目
    val sVectorTwo: Vector = Vectors.sparse(4, Seq((0, 1.0), (2, 2.0), (3, 3.0)))
    println(sVectorTwo)

    //    Spark 提供了多种方式来访问和查看向量数值，比如：
    val sVectorOneMax = sVectorOne.argmax
    val sVectorOneNumNonZeros = sVectorOne.numNonzeros
    val sVectorOneSize = sVectorOne.size
    val sVectorOneArray = sVectorOne.toArray
    val sVectorOneJson = sVectorOne.toJson
    println("sVectorOneMax:" + sVectorOneMax)
    println("sVectorOneNumNonZeros:" + sVectorOneNumNonZeros)
    println("sVectorOneSize:" + sVectorOneSize)
    println("sVectorOneArray:" + sVectorOneArray)
    println("sVectorOneJson:" + sVectorOneJson)
    val dVectorOneToSparse = dVectorOne.toSparse
    println(dVectorOneToSparse)
  }

  def main(args: Array[String]): Unit = {
    // 复数
    complexDemo()
    // 向量
    vectorDemo()
    // spark中的向量包使用
    vectorSparkDemo()
  }
}
