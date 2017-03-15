/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ydl.learning.spark

import java.util.{Properties, UUID}

import com.aliyun.openservices.ons.api.impl.ONSFactoryImpl
import com.aliyun.openservices.ons.api.{Message, PropertyKeyConst}
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.aliyun.ons.OnsUtils
import org.apache.spark.streaming.{Milliseconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}
import org.slf4j.LoggerFactory

object AliyunONSSample {
  def main(args: Array[String]): Unit = {
    val log = LoggerFactory.getLogger(AliyunONSSample.getClass)
    val config = new Array[String](6)
    config(0) = "eLiJCyOQT5BiUAYn"
    config(1) = "HJth7lyCgX3jC2FScH6aM90V0FwXmT"
    config(2) = "CID_YDL_TEST"
    config(3) = "ydltest"
    config(4) = "*"
    config(5) = "1"
    if (config.length < 6) {
      System.err.println(
        """Usage: bin/spark-submit --class ONSSample examples-1.0-SNAPSHOT-shaded.jar <accessKeyId> <accessKeySecret>
          |         <consumerId> <topic> <subExpression> <parallelism>
          |
          |Arguments:
          |
          |    accessKeyId      Aliyun Access Key ID.
          |    accessKeySecret  Aliyun Key Secret.
          |    consumerId       ONS ConsumerID.
          |    topic            ONS topic.
          |    subExpression    * for all, or some specific tag.
          |    parallelism      The number of receivers.
          |
        """.stripMargin)
      System.exit(1)
    }

    val Array(accessKeyId, accessKeySecret, cId, topic, subExpression, parallelism) = config

    val numStreams = parallelism.toInt
    val batchInterval = Milliseconds(5000)

    val conf = new SparkConf().setAppName("ONS Sample")
    if (args.length == 0) {
      //本地调试不带参数直接使用local 4表示使用cpu4核，spark中转化操作占用一个core，行动操作占用一个，所以本地调试时最少要[2]
      conf.setMaster("local[4]")
    }
    else
      conf.setMaster("spark://192.168.248.25:7077")
    val ssc = new StreamingContext(conf, batchInterval)
    def func: Message => Array[Byte] = msg => {
      println(msg.getMsgID)
      println(msg.toString)
      (new String(msg.getBody) + "|" + msg.getTag).getBytes
    }
    val onsStreams = (0 until numStreams).map { i =>
      println(s"starting stream $i")
      OnsUtils.createStream(
        ssc,
        cId,
        topic,
        subExpression,
        accessKeyId,
        accessKeySecret,
        StorageLevel.MEMORY_AND_DISK_2,
        func)
    }
    val unionStreams = ssc.union(onsStreams)
    unionStreams.foreachRDD(rdd => {
      rdd.map(bytes => new String(bytes)).collect().foreach {
        msg =>
          println(msg)
          log.info(msg)
      }
    })
    ssc.start()
    ssc.awaitTermination()
  }
}

object OnsRecordProducer {
  def main(args: Array[String]): Unit = {
    val Array(accessKeyId, accessKeySecret, pId, topic, tag, parallelism) = args

    val numPartition = parallelism.toInt
    val conf = new SparkConf().setAppName("ONS Record Producer")
    val sc = new SparkContext(conf)

    sc.parallelize(0 until numPartition, numPartition).mapPartitionsWithIndex {
      (index, itr) => {
        generate(index, accessKeyId, accessKeySecret, pId, topic, tag)
        Iterator.empty
      }
    }.count()
  }

  def generate(
                partitionId: Int,
                accessKeyId: String,
                accessKeySecret: String,
                pId: String,
                topic: String,
                tag: String): Unit = {
    val properties = new Properties()
    properties.put(PropertyKeyConst.ProducerId, pId)
    properties.put(PropertyKeyConst.AccessKey, accessKeyId)
    properties.put(PropertyKeyConst.SecretKey, accessKeySecret)
    val onsFactoryImpl = new ONSFactoryImpl
    val producer = onsFactoryImpl.createProducer(properties)
    producer.shutdown()
    producer.start()

    var count = 0
    while (true) {
      val uuid = UUID.randomUUID()
      val msg = new Message(topic, tag, uuid.toString.getBytes)
      msg.setKey(s"ORDERID_${partitionId}_$count")
      producer.send(msg)
      count += 1
      Thread.sleep(100L)
    }
  }
}
