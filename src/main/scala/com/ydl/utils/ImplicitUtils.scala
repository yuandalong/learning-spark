package com.ydl.utils

import java.util
import java.util.Date

import org.apache.hadoop.hbase.util.Bytes
import org.slf4j.LoggerFactory

/**
  * String隐式转换工具类
  *
  * Created by ydl on 2018/1/16.
  */
class StringUtils(str: String) {
  /**
    * 空判断
    *
    * @return
    */
  def isNull: Boolean = {
    str == null || str.isEmpty
  }

  /**
    * 非空判断
    *
    * @return
    */
  def isNotNull: Boolean = {
    !isNull
  }

  /**
    * 左补零到8位
    *
    * @return
    */
  def zero: String = {
    zero(8)
  }

  /**
    * 字符串左补零
    *
    * @param lang 最终字符串长度
    * @return
    */
  def zero(lang: Int): String = {
    if (isNotNull && str.length < lang) {
      val strLength = str.length
      val byteArray = new Array[Char](lang - strLength)
      for (i <- byteArray.indices) {
        byteArray(i) = '0'
      }
      new String(byteArray).concat(str)
    }
    else if (isNull) {
      val r = new StringBuilder
      for (_ <- 0 until lang)
        r.append(0)
      r.toString()
    } else if (str.length > lang) {
      val result = str.substring(str.length - lang, str.length)
      StringUtils.log.warn(s"字符串超长:$str,$lang,$result")
      result
    } else {
      str
    }
  }

  /**
    * 转int
    *
    * @param defaultValue
    * @return
    */
  def asInt(defaultValue: Int): Int = {
    if (isNull) defaultValue else Integer.valueOf(str).intValue()
  }

  /**
    * 转int，默认0
    *
    * @return
    */
  def asInt: Int = {
    asInt(0)
  }

  /**
    * 字符串拼接
    *
    * @param str1
    * @param strs
    * @return
    */
  def concat(str1: String, strs: String*): String = {
    val sb = new StringBuilder
    sb.append(str).append(str1)
    if (strs.nonEmpty) {
      for (s <- strs) {
        sb.append(s)
      }
    }
    sb.toString
  }

  def reversal: Long = {
    Long.MaxValue - asLong
  }

  /**
    * 是否uuid串
    *
    * @return
    */
  def isUUID: Boolean = {
    str.length == 36 && str.indexOf("-") == 8 && str.lastIndexOf("-") == 23
  }

  /**
    * 转成日期 yyyy-MM-dd
    *
    * @return
    */
  def asDate: Date = {
    DateUtils.string2Date(str, DateUtils.FORMAT3)
  }

  /**
    * 转成日期 yyyyMMdd
    *
    * @return
    */
  def asDate2: Date = {
    DateUtils.string2Date(str, DateUtils.FORMAT4)
  }

  /**
    * 转成时间 yyyy-MM-dd HH:mm:ss
    *
    * @return
    */
  def asDateTime: Date = {
    DateUtils.string2Date(str, DateUtils.FORMAT1)
  }

  def asShort: Short = {
    str.toShort
  }

  /**
    * 转成字节数组
    *
    * @return
    */
  def asBytes: Array[Byte] = {
    if (isNull) {
      null
    }
    else {
      Bytes.toBytes(str)
    }
  }

  def asLong: java.lang.Long = {
    java.lang.Long.valueOf(str)
  }

  /**
    * 替换Emoji表情
    *
    * @return
    */
  def replaceEmoji: String = {
    str.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "")
  }

  def asBoolean: Boolean = {
    if (str == "1" || str == "true") {
      true
    }
    else {
      false
    }
  }

  /**
    * 替换所有中划线
    *
    * @return
    */
  def replaceBar: String = {
    str.replaceAll("-", "")
  }

  lazy private val md5handle = java.security.MessageDigest.getInstance("MD5")
  private val hexDigits =
    Array[Char]('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f')

  /**
    * md5加密
    *
    * @return
    */
  def md5: String = {
    val encrypt = md5handle.digest(str.getBytes)
    val b = new StringBuilder(32)
    for (i <- 0.to(15)) {
      b.append(hexDigits(encrypt(i) >>> 4 & 0xf)).append(hexDigits(encrypt(i) & 0xf))
    }
    b.mkString
  }
}

object StringUtils {
  private val log = LoggerFactory.getLogger(classOf[StringUtils])
}

/**
  * duble
  *
  * @param d
  */
class DoubleUtils(d: java.lang.Double) {
  /**
    * Double是否为0判断，null也视为0
    *
    * @return
    */
  def isZero: Boolean = {
    d == null || d.doubleValue() == 0
  }

  def isNotZero: Boolean = {
    !isZero
  }

  def asBytes: Array[Byte] = {
    Bytes.toBytes(d)
  }

  def asDecimal: java.math.BigDecimal = {
    java.math.BigDecimal.valueOf(d)
  }
}

class ScalaDoubleUtils(d: Double) {
  val utils = new DoubleUtils(d)

  def asBytes: Array[Byte] = utils.asBytes

  def asDecimal: java.math.BigDecimal = utils.asDecimal
}

/**
  * list
  *
  * @param l
  * @tparam E
  */
class ListUtils[E](l: java.util.List[E]) {
  def isNull: Boolean = {
    l == null || l.isEmpty
  }

  def isNotNull: Boolean = {
    !isNull
  }


  /**
    * 列表分页
    *
    * @return
    */
  def getPages: Set[List[E]] = {
    import scala.collection.JavaConverters._
    var result = Set[List[E]]()
    var data2 = l.asScala.toList
    if (isNotNull) {
      val pageCount = ImplicitUtils.getPageCount(l.size())
      for (_ <- 1 to pageCount) {
        var list = List[E]()
        list = data2.take(ImplicitUtils.PAGE_SIZE)
        data2 = data2.drop(ImplicitUtils.PAGE_SIZE)
        result = result + list
      }
    }
    result
  }
}

class ScalaListUtils[E](l: List[E]) {
  def isNull: Boolean = {
    l == null || l.isEmpty
  }

  def isNotNull: Boolean = {
    !isNull
  }

  /**
    * 列表分页
    *
    * @return
    */
  def getPages: Set[List[E]] = {
    var result = Set[List[E]]()
    var data2 = l
    if (isNotNull) {
      val pageCount = ImplicitUtils.getPageCount(l.length)
      for (_ <- 1 to pageCount) {
        var list = List[E]()
        list = data2.take(ImplicitUtils.PAGE_SIZE)
        data2 = data2.drop(ImplicitUtils.PAGE_SIZE)
        result = result + list
      }
    }
    result
  }
}

/**
  * date
  *
  * @param d
  */
class DateUtilsScala(d: java.util.Date) {
  /**
    * 时段间隔，单位分
    */
  private val INTERVAL = 15

  /**
    * Calendar 对象
    */

  import java.util.Calendar


  /**
    * 获取时段 15分钟一个时段
    *
    * @return
    */
  def getTimeInterval: String = {
    var result = ":00"
    (getMinute - 1) / INTERVAL match {
      case 1 => result = ":15"
      case 2 => result = ":30"
      case 3 => result = ":45"
      case _ =>
    }
    getHourStr(getHour) + result
  }

  def getHourStr(hour: Int): String = {
    if (hour < 10)
      "0" + hour
    else
      hour.toString
  }

  /**
    * 获取时段，30分钟一个时段
    */
  def getTimeInterval2: String = {
    var result = ":00"
    if (getMinute > 30)
      result = ":30"
    getHourStr(getHour) + result
  }

  /**
    * 获取分钟
    *
    * @return
    */
  def getMinute: Int = {
    val calendar = Calendar.getInstance
    calendar.setTime(d)
    calendar.get(Calendar.MINUTE)
  }

  /**
    * 获取小时，24小时制
    *
    * @return
    */
  def getHour: Int = {
    val calendar = Calendar.getInstance
    calendar.setTime(d)
    calendar.get(Calendar.HOUR_OF_DAY)
  }

  def asString: String = {
    if (d == null) {
      ""
    }
    else {
      DateUtils.date2String(d, DateUtils.FORMAT3)
    }
  }

  def asString2: String = {
    if (d == null) {
      ""
    }
    else {
      DateUtils.date2String(d, DateUtils.FORMAT4)
    }
  }

  def asTimeStr: String = {
    DateUtils.date2String(d, DateUtils.FORMAT5)
  }

}

class LongUtils(l: java.lang.Long) {
  /**
    * 非0判断，null按0处理
    *
    * @return
    */
  def isNotZero: Boolean = {
    !isZero
  }

  /**
    * 0判断，null按0处理
    *
    * @return
    */
  def isZero: Boolean = {
    l == null || l.intValue() == 0
  }

  def asBytes: Array[Byte] = {
    Bytes.toBytes(l)
  }

  /**
    * 取Long最大值减当前值的String，用于hbase倒排
    *
    * @return
    */
  def reversal: String = {
    val sUtils = new StringUtils((Long.MaxValue - l).toString)
    sUtils.zero(19)

  }

  def asString: String = {
    if (l == null) {
      "0"
    }
    else {
      l.toString
    }
  }

  def asLong: Long ={
    if (l == null){
      0L
    }
    else{
      l.longValue()
    }
  }

}

class ScalaLongUtils(l: Long) {
  private val util = new LongUtils(l)

  def reversal: String = util.reversal

  def asString: String = util.asString

  def asBytes: Array[Byte] = util.asBytes
}

class ShortUtils(s: java.lang.Short) {
  def asBytes: Array[Byte] = {
    Bytes.toBytes(s)
  }

  def isZero: Boolean = {
    s == null || s.intValue() == 0
  }

  def isNotZero: Boolean = {
    !isZero
  }
}

class IntegerUtils(i: Integer) {
  def asBytes: Array[Byte] = {
    if (i == null)
      Bytes.toBytes(0)
    else
      Bytes.toBytes(i)
  }

  def asInt:Int={
    if(i == null){
      0
    }
    else{
      i.toInt
    }
  }
}

class IntUtils(i: Int) {
  private val util = new IntegerUtils(i)

  def asBytes: Array[Byte] = {
    util.asBytes
  }
}

class DecimalUtils(d: java.math.BigDecimal) {
  def +(dou: java.lang.Double): java.math.BigDecimal = {
    d.add(new java.math.BigDecimal(dou))
  }

  def +(i: Int): java.math.BigDecimal = {
    d.add(new java.math.BigDecimal(i))
  }

  def +(dec: java.math.BigDecimal): java.math.BigDecimal = {
    d.add(dec)
  }

  def -(dou: java.lang.Double): java.math.BigDecimal = {
    d.subtract(new java.math.BigDecimal(dou))
  }

  def -(i: Int): java.math.BigDecimal = {
    d.subtract(new java.math.BigDecimal(i))
  }

  def -(dec: java.math.BigDecimal): java.math.BigDecimal = {
    d.subtract(dec)
  }

  def *(i: Int): java.math.BigDecimal = {
    try {
      d.multiply(java.math.BigDecimal.valueOf(i))
    } catch {
      case _: Throwable => java.math.BigDecimal.ZERO
    }
  }

  def isNotZero: Boolean = {
    d != null && d.doubleValue() != 0
  }

  def <(a: java.math.BigDecimal): Boolean = {
    d.compareTo(a) == -1
  }

  def >(a: java.math.BigDecimal): Boolean = {
    d.compareTo(a) == 1
  }

  /**
    * 四舍五入保留两位小数
    *
    * @return
    */
  def format: java.math.BigDecimal = {
    d.setScale(2, java.math.BigDecimal.ROUND_HALF_UP)
  }

}

class ScalaMapUtils[K, V](m: Map[K, V]) {
  def asJava: java.util.Map[K, V] = {
    val result = new util.HashMap[K, V]()
    m.foreach(a => {
      result.put(a._1, a._2)
    })
    result
  }
}

class BytesUtils(b: Array[Byte]) {
  def asString: String = {
    Bytes.toString(b)
  }

  def asInt: Int = {
    Bytes.toInt(b)
  }

  def asShort: Short = {
    Bytes.toShort(b)
  }

  def asLong: Long = {
    Bytes.toLong(b)
  }

  def asDouble: Double = {
    Bytes.toDouble(b)
  }

  def asBool: Boolean = {
    Bytes.toBoolean(b)
  }
}

object Transform {
  implicit def stringUtils(str: String): StringUtils = new StringUtils(str)

  implicit def doubleUtils(d: java.lang.Double): DoubleUtils = new DoubleUtils(d)

  implicit def scalaDoubleUtils(d: Double): ScalaDoubleUtils = new ScalaDoubleUtils(d)

  implicit def listUtils[E](l: java.util.List[E]): ListUtils[E] = new ListUtils(l)

  implicit def scalaListUtils[E](l: List[E]): ScalaListUtils[E] = new ScalaListUtils(l)

  implicit def dateUtils(d: java.util.Date): DateUtilsScala = new DateUtilsScala(d)

  implicit def longUtils(l: java.lang.Long): LongUtils = new LongUtils(l)

  implicit def shortUtils(s: java.lang.Short): ShortUtils = new ShortUtils(s)

  implicit def decimalUtils(d: java.math.BigDecimal): DecimalUtils = new DecimalUtils(d)

  implicit def scalaMapUtils[K, V](m: Map[K, V]): ScalaMapUtils[K, V] = new ScalaMapUtils(m)

  implicit def bytesUtils(b: Array[Byte]): BytesUtils = new BytesUtils(b)

  implicit def integerUtils(i: Integer): IntegerUtils = new IntegerUtils(i)

  implicit def intUtils(i: Int): IntUtils = new IntUtils(i)

  implicit def scalaLongUtils(l: Long): ScalaLongUtils = new ScalaLongUtils(l)
}

object ImplicitUtils {
  def getPageCount(count: Int): Int = {
    if (count <= 0) {
      count
    }
    else {
      if (count % PAGE_SIZE == 0) {
        count / PAGE_SIZE
      }
      else {
        count / PAGE_SIZE + 1
      }
    }
  }

  val PAGE_SIZE = 20

  def main(args: Array[String]): Unit = {
    import Transform._
    println(1000001600086L.reversal)
    val b = (0.02).asDecimal
    println(b.isNotZero)

  }
}
