package com.ydl.learning.spark.streaming

import java.util.Properties

import com.ydl.utils.Transform._
import com.ydl.utils.{HbaseUtils, KafkaUtils}
import org.apache.hadoop.hbase.client.{ConnectionFactory, Scan, Table}
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.hbase.{HBaseConfiguration, TableName}
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.{PartitionInfo, TopicPartition}
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, HasOffsetRanges, OffsetRange}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

/**
  * kafka消费者demo
  *
  * @author ydl
  * @since 2018/11/12
  */
object KafkaCustomerDemo {

  def main(args: Array[String]) {

    val batchDuration = 10
    val bootstrapServers = "192.168.49.62:9092"
    val topicRegexp = "test"

    val consumerGroupID = "ydl"
    val hbaseTableName = "stream_kafka_offsets"
    val hbaseConf = HBaseConfiguration.create()
    hbaseConf.set("hbase.zookeeper.quorum", "localhost:2181")
    val connection = ConnectionFactory.createConnection(hbaseConf)
    val table: Table = connection.getTable(TableName.valueOf(hbaseTableName))
    val sparkConf = new SparkConf().setAppName("Kafka-Offset-Management-Blog")
      .setMaster("local[4]")
    val sc = new SparkContext(sparkConf)
    val ssc = new StreamingContext(sc, Seconds(batchDuration.toLong))

    val kafkaParamsMap = Map[String, Object](
      "bootstrap.servers" -> bootstrapServers,
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> consumerGroupID,
      "auto.offset.reset" -> "earliest",
      "enable.auto.commit" -> (false: java.lang.Boolean)
    )
    val kafkaParams = new Properties()
    kafkaParamsMap.foreach(kv => {
      kafkaParams.put(kv._1, kv._2)
    })
    println(kafkaParams)
    val kafkaUtils = KafkaUtils(kafkaParams)
    val partitionList = kafkaUtils.getParForPattern(topicRegexp)
    println(s"kafka partition list $partitionList")
    val beginOffsetMap = kafkaUtils.getBeginOffsets2(partitionList)
    println(s"kafka begin offset $beginOffsetMap")

    /*
    Create a dummy process that simply returns the message as is.
     */
    def processMessage(message: ConsumerRecord[String, String]): ConsumerRecord[String, String] = {
      //      println(message.value())
      message
    }

    /**
      * 保存kafka offset，每个分区一列
      *
      * @param TOPIC_NAME
      * @param GROUP_ID
      * @param offsetRanges
      * @param table
      * @param batchTime
      */
    def saveOffsets(TOPIC_NAME: String, GROUP_ID: String, offsetRanges: Array[OffsetRange], table: Table,
                    batchTime: org.apache.spark.streaming.Time): Unit = {
      val rowKey = TOPIC_NAME + ":" + GROUP_ID + ":" + String.valueOf(batchTime.milliseconds)
      HbaseUtils.hbaseSaveHelper(rowKey, table, put => {
        for (offset <- offsetRanges) {
          put.addColumn(Bytes.toBytes("cf"), Bytes.toBytes(offset.partition.toString),
            Bytes.toBytes(offset.untilOffset.toString))
        }
      })
    }

    /**
      * 获取kafka offset
      * 情形1：Streaming任务第一次启动，从kafka中获取给定topic的分区数和offset的最小值，并返回。
      * 注意此种情况下不能直接将offset设置为0，因为kafka会定时清理日志，如果指定的offset小于当前kafka的最小offset，会报OffsetOutOfRangeException
      *
      * 情形2：一个运行了很长时间的streaming任务停止并且给定的topic增加了新的分区，处理方式是从kafka中获取给定topic的分区数，
      * 对于所有老的分区，offset依然使用HBase中所保存，对于新的分区则将offset设置为offset的最小值。
      *
      * 情形3：Streaming任务长时间运行后停止并且topic分区没有任何变化，在这个情形下，直接使用HBase中所保存的offset即可。
      *
      * 在Spark Streaming应用启动之后如果topic增加了新的分区，那么应用只能读取到老的分区中的数据，新的是读取不到的。
      * 所以如果想读取新的分区中的数据，那么就得重新启动Spark Streaming应用
      */
    def getOffsets(partitionList: List[PartitionInfo], hbaseTable: Table, kafkaGroupId: String,
                   beginOffsetMap: mutable.Map[TopicPartition, Long]) = {
      println("starting getOffsets")
      //起始offset map
      val fromOffsets = collection.mutable.Map[TopicPartition, Long]()
      partitionList.groupBy(_.topic).foreach(topicGroup => {
        println(topicGroup)
        val topic = topicGroup._1
        val startRow = topic + ":" + kafkaGroupId + ":" + String.valueOf(System.currentTimeMillis())
        val stopRow = topic + ":" + kafkaGroupId + ":" + 0
        val scan = new Scan()
        val scanner = table.getScanner(scan.setStartRow(startRow.getBytes).setStopRow(stopRow.getBytes).setReversed(true))
        val result = scanner.next()
        //topic在kafka中的分区数
        val partitionCount = topicGroup._2.size
        //topic在hbase里有记录的分区数
        val partitionCountInHbase = if (result == null) 0 else result.listCells().size
        //新应用，hbase里没有offset数据
        if (partitionCountInHbase == 0) {
          println("hbase中没有offset数据")
          for (partition <- 0 until partitionCount) {
            val tp = new TopicPartition(topic, partition)
            fromOffsets += (tp -> beginOffsetMap.getOrElse(tp, 0L))
          }
        }
        //有新创建的分区
        else if (partitionCount > partitionCountInHbase) {
          println("kafka有新创建的分区")
          //kafka分区是连续的，新创建的分区肯定排后面
          for (partition <- 0 until partitionCountInHbase) {
            fromOffsets += (new TopicPartition(topic, partition) -> Bytes.toString(result.getValue(Bytes.toBytes("offsets"), Bytes.toBytes(partition.toString))).asLong)
          }
          for (partition <- partitionCountInHbase until partitionCount) {
            val tp = new TopicPartition(topic, partition)
            fromOffsets += (tp -> beginOffsetMap.getOrElse(tp, 0L))
          }
        }
        //分区数一致
        else {
          println("kafka分区数一致")
          for (partition <- 0 until partitionCountInHbase) {
            fromOffsets += (new TopicPartition(topic, partition) -> Bytes.toString(result.getValue(Bytes.toBytes("cf"), Bytes.toBytes(partition.toString))).asLong)
          }
        }
        scanner.close()
      })
      fromOffsets.toMap
    }


    val fromOffsets = getOffsets(partitionList, table, consumerGroupID, beginOffsetMap)
    val inputDStream = org.apache.spark.streaming.kafka010.KafkaUtils.createDirectStream[String, String](ssc,
      PreferConsistent, ConsumerStrategies.Assign[String, String](fromOffsets.keys, kafkaParamsMap, fromOffsets))

    /*
      For each RDD in a DStream apply a map transformation that processes the message.
    */
    inputDStream.foreachRDD((rdd, batchTime) => {
      val newRDD = rdd.map(message => processMessage(message))
      println(s"数据条数:${newRDD.count()}")
      val offsetRanges = rdd.asInstanceOf[HasOffsetRanges].offsetRanges
      offsetRanges.foreach(offset => {
        println("kafka消费信息：", offset.topic, offset.partition, offset.fromOffset, offset.untilOffset)
        if (offset.fromOffset == offset.untilOffset) {
          println(s"${offset.topic}:${offset.partition} no new data")
        }
        else {
          saveOffsets(offset.topic, consumerGroupID, offsetRanges, table, batchTime) //save the offsets to HBase
        }
      })

    })
    //
    ssc.start()
    ssc.awaitTermination()
  }
}


