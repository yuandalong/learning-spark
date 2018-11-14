package com.ydl.utils

import java.util.Properties
import java.util.regex.Pattern

import org.apache.kafka.clients.consumer.{ConsumerConfig, KafkaConsumer}
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.{PartitionInfo, TopicPartition}

import scala.collection.JavaConverters._
import scala.collection.mutable

/**
  * kafka工具类
  *
  * @author ydl
  * @since 2018/11/12
  */
class KafkaUtils(properties: Properties) {
  val consumer = new KafkaConsumer[String, String](properties)

  /**
    * 获取topic列表
    */
  def getTopicList: Set[String] = {
    consumer.listTopics().keySet().asScala.toSet
  }

  /**
    * 获取单个topic分区信息
    *
    * @param topic topic名称
    * @return
    */
  def getPartitions(topic: String): List[PartitionInfo] = {
    consumer.partitionsFor(topic).asScala.toList
  }

  /**
    * 获取多个topic的分区信息
    *
    * @param topicList topic列表
    * @return
    */
  def getParForTopicList(topicList: List[String]): List[PartitionInfo] = {
    var result: List[PartitionInfo] = List()
    topicList.map(getPartitions).foreach(par => result = result ++ par)
    result
  }

  /**
    * 正则获取topic列表
    *
    * @param regexp
    * @return
    */
  def getTopicListForPattern(regexp: String): List[String] = {
    val topicList = getTopicList
    var result: List[String] = List()
    if (topicList != null && topicList.nonEmpty) {
      topicList.foreach(topic => {
        if (Pattern.matches(regexp, topic)) {
          result = result :+ topic
        }
      })
    }
    result
  }

  /**
    * 正则获取topic分区信息
    *
    * @param regexp
    */
  def getParForPattern(regexp: String): List[PartitionInfo] = {
    getParForTopicList(getTopicListForPattern(regexp))
  }

  /**
    * 获取topic指定分区的开始offset
    * kafka会定时清理日志，如果指定的offset小于当前kafka的最小offset，会报OffsetOutOfRangeException，所以要去kafka里取最小的offset，而不是直接指定0
    *
    * @param topic
    * @param partition
    */
  def getBeginOffset(topic: String, partition: Int): Long = {
    val topicPartition = new TopicPartition(topic, partition)
    getBeginOffsets(List(topicPartition)).getOrElse(topicPartition, 0L)
  }

  /**
    * 批量获取起始offset
    *
    * @param topicPartitionList
    * @return
    */
  def getBeginOffsets(topicPartitionList: List[TopicPartition]): mutable.Map[TopicPartition, Long] = {
    consumer.beginningOffsets(topicPartitionList.asJava).asScala.map(m=>(m._1,m._2.toLong))
  }

  /**
    * 批量获取起始offset
    * @param partitions
    * @return
    */
  def getBeginOffsets2(partitions: List[PartitionInfo]):mutable.Map[TopicPartition, Long]={
    getBeginOffsets(partitions.map(info=>new TopicPartition(info.topic(),info.partition())))
  }
}

object KafkaUtils {
  def apply(properties: Properties): KafkaUtils = new KafkaUtils(properties)

  def main(args: Array[String]): Unit = {
    val properties = new Properties()
    properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer]) // key反序列化方式
    properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer]) // value反系列化方式
    properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true") // 提交方式
    properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.49.62:9092"); // 指定broker地址，来找到group的coordinator
    properties.put(ConsumerConfig.GROUP_ID_CONFIG, "ydl") // 指定用户组
    val utils = new KafkaUtils(properties)
    //    println(utils.getTopicList)
    //    println(utils.getPartitions("test"))
    //    println(utils/**/.getParForTopicList(List("test", "acc_bill_pre_04")))
    //    val regexp = "(trans_water_\\d{2})|(membership)|(coupon)|(ado_pos_wait_\\d{2})|(ado_bill_\\d{2})"
    //    println(utils.getTopicListForPattern(regexp))
    //    utils.getParForPattern(regexp).foreach(println)
    utils.getBeginOffset("membership", 0)
  }
}
