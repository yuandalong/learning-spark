package com.ydl.utils

import java.util.UUID

import org.apache.hadoop.hbase.Cell
import org.apache.hadoop.hbase.client._
import org.apache.hadoop.hbase.filter.PageFilter
import org.apache.hadoop.hbase.util.Bytes
import org.slf4j.{Logger, LoggerFactory}

import scala.collection.JavaConverters._
import scala.util.control.Breaks._
import Transform._

/**
  * hbase操作基础父类
  *
  * Created by ydl on 2018/4/8.
  */
object HbaseUtils {
  val log: Logger = LoggerFactory.getLogger(this.getClass)
  /**
    * hbase默认列簇名
    */
  val DEFAULT_CF_NAME: Array[Byte] = "cf".asBytes
  val COLUMN_NAME_ID_S = "id"
  val COLUMN_NAME_ID: Array[Byte] = COLUMN_NAME_ID_S.asBytes
  val COLUMN_NAME_STORE_ID_S = "sid"
  val COLUMN_NAME_STORE_ID: Array[Byte] = COLUMN_NAME_STORE_ID_S.asBytes
  val COLUMN_NAME_BUSS_DATE_S = "b"
  val COLUMN_NAME_BUSS_DATE: Array[Byte] = COLUMN_NAME_BUSS_DATE_S.asBytes
  /**
    * long最大值
    */
  val LONG_MAX_VALUE = "9223372036854775807"
  /**
    * 19个0
    */
  val LONG_MIN_VALUE = "0000000000000000000"
  val MAX_DATE = "21080101"
  val MIN_DATE = "20080101"
  val DEFAULT_PAGE_SIZE = 1000


  /**
    * 生成日期加UUID格式的rowKey
    * 最后一位会追加一个0，这样分页的时候下一页StartRow最后一位替换成1就可以
    *
    * @param bussDate
    * @return
    */
  def getDateAndUUIDRowKey(bussDate: String): String = {
    (bussDate concat UUID.randomUUID().toString).replaceBar concat "0"
  }

  /**
    * 下一个uuid格式的rowKey
    * uuid结尾的rowKey需要最后一位加0，所以此处就是把最后的0替换成1
    *
    * @param rowKey
    * @return
    */
  def getNextUUIDRowKey(rowKey: String): String = {
    rowKey.substring(0, rowKey.length - 1) concat "1"
  }

  /**
    * 下一个数字格式的rowKey
    * 需调用方保证rowKey以数字结尾
    *
    * @param rowKey
    * @return
    */
  def getNextNumbRowKey(rowKey: String): String = {
    if (!rowKey.endsWith("9")) {
      rowKey.substring(0, rowKey.length - 1) concat (rowKey.substring(rowKey.length - 1).asInt + 1).asString
    }
    else {
      //非9倒序索引
      var notZeroIndex = 1
      breakable {
        rowKey.split("").reverse.foreach(s => {
          if (s == "9") {
            notZeroIndex = notZeroIndex + 1
          }
          else {
            break
          }
        })
      }
      //全是9
      if (notZeroIndex > rowKey.length) {
        "1" concat "".zero(rowKey.length)
      }
      else {
        rowKey.substring(0, rowKey.length - notZeroIndex) concat (rowKey.substring(rowKey.length - notZeroIndex).asInt + 1).asString
      }
    }
  }

  /**
    * hbase分页
    *
    * @param scan
    * @param table
    * @param pageSize
    * @param f
    */
  def hbasePageHelper(scan: Scan, table: Table, pageSize: Int, f: ResultScanner => (Int, String)): Unit = {
    if (scan == null) {
      throw new Exception("hbase scan is null")
    }
    if (scan.getStartRow.isEmpty || scan.getStopRow.isEmpty) {
      throw new Exception("startRow or stopRow is null")
    }
    val pageFilter = new PageFilter(pageSize)
    scan.setFilter(pageFilter)
    var startRow = ""
    var isLastPage = false
    while (!isLastPage) {
      if (startRow.isNotNull) {
        scan.setStartRow(Bytes.toBytes(startRow))
      }
      val scanner = table.getScanner(scan)
      //执行函数
      val result = f(scanner)
      val i = result._1
      startRow = result._2
      //最后一页或者超过最大条数时退出
      if (i < pageSize || i == 0) {
        isLastPage = true
      }
      scanner.close()
    }
  }

  /**
    * hbase分页，指定字段
    *
    * @param table
    * @param columns
    * @param f
    */
  def hbasePageHelper(scan: Scan, table: Table, pageSize: Int, columns: List[Array[Byte]], f: ResultScanner => (Int, String)): Unit = {
    if (columns.isNull) {
      throw new Exception("columns is null")
    }
    for (b <- columns) {
      scan.addColumn(DEFAULT_CF_NAME, b)
    }
    hbasePageHelper(scan, table, pageSize, f)

  }

  /**
    * hbase put
    *
    * @param rowKey
    * @param table
    * @param f
    */
  def hbaseSaveHelper(rowKey: String, table: Table, f: Put => Unit): Unit = {
    val put = new Put(rowKey.asBytes)
    //字段赋值
    f(put)
    table.put(put)
  }

  /**
    * hbase put,set timestamp
    *
    * @param rowKey
    * @param timestamp
    * @param table
    * @param f
    */
  def hbaseSaveHelper(rowKey: String, timestamp: Long, table: Table, f: Put => Unit): Unit = {
    val put = new Put(rowKey.asBytes, timestamp)
    //字段赋值
    f(put)
    table.put(put)
  }

  /**
    * 查所有数据
    *
    * @param scan
    * @param table
    * @param f
    */
  def hbaseScanAllHelper(scan: Scan, table: Table, f: ResultScanner => Unit): Unit = {
    if (scan == null) {
      throw new Exception("hbase scan is null")
    }
    val scanner = table.getScanner(scan)
    //执行函数
    f(scanner)
    scanner.close()
  }

  /**
    * 查所有数据
    *
    * @param startRow 开始row
    * @param stopRow  结束row
    * @param columns  查询字段
    * @param table    查询表
    * @param f        执行函数
    */
  def hbaseScanAllHelper(startRow: String, stopRow: String, columns: Array[String], table: Table, f: ResultScanner => Unit): Unit = {
    if (columns.isEmpty) {
      throw new Exception("hbase columns is null")
    }
    val scan = new Scan
    scan.setStartRow(startRow.asBytes)
    scan.setStopRow(stopRow.asBytes)
    columns.foreach(column => {
      scan.addColumn(DEFAULT_CF_NAME, column.asBytes)
    })
    val scanner = table.getScanner(scan)
    f(scanner)
    scanner.close()
  }

  /**
    * 根据rowkey查具体数据
    *
    * @param rowKey     rowKey
    * @param columns    需返回字段
    * @param table      hbase表
    * @param maxVersion 范湖最大版本数，默认为0
    * @param f          高阶函数,注意没有数据时不执行该函数
    */
  def hbaseGetHelper(rowKey: String, columns: Array[String], table: Table, maxVersion: Int = 0, f: List[Cell] => Unit): Unit = {
    if (columns.isEmpty) {
      throw new Exception("hbase columns is null")
    }
    val get = new Get(rowKey.asBytes)
    columns.foreach(column => {
      get.addColumn(DEFAULT_CF_NAME, column.asBytes)
    })
    if (maxVersion > 0)
      get.setMaxVersions(maxVersion)
    val cells = table.get(get).listCells
    if (cells.isNotNull)
      f(cells.asScala.toList)
  }

  /**
    * 批量删除
    *
    * @param startRow 开始
    * @param stopRow  结束
    * @param columns  查询用到的字段，指定至少一个字段且该字段必须存在，防止hbase服务器返回所有字段或者字段不存在导致查不到数据
    * @param table    表
    */
  def hbaseDelHelper(startRow: String, stopRow: String, columns: Array[String], table: Table): Unit = {
    //此处不能用scala的list，hbase源码里会做list的remove，scala的list不支持remove
    val dels = new java.util.ArrayList[Delete]()
    hbaseScanAllHelper(startRow, stopRow, columns, table, scan => {
      scan.asScala.foreach(result => {
        dels.add(new Delete(result.getRow))
      })
    })
    if (dels.isNotNull) {
      table.delete(dels)
    }
  }

  /**
    * 单条删除
    *
    * @param rowKey
    * @param table
    */
  def hbaseDelHelper(rowKey: String, table: Table): Unit = {
    val del = new Delete(rowKey.asBytes)
    table.delete(del)
  }
}
