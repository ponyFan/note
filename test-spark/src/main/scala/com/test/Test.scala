package com.test

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka.KafkaUtils

/**
 * @author zelei.fan
 * @date 2019/9/19 9:42
 */
object Test {

  def main(args: Array[String]): Unit = {
    val ssc = new StreamingContext(new SparkConf(), Seconds(5))
    val topics = Array(args(0)).toSet
    val kafkaParams = Map[String, String](
      "bootstrap.servers" -> "172.22.6.19:9092,172.22.6.20:9092,172.22.6.21:9092",
      "auto.offset.reset" -> "earliest"
    )
    val DStream = KafkaUtils.createDirectStream(ssc, kafkaParams, topics)
    DStream.foreachRDD(rdd => {
      rdd.foreach(line => {
        println("key=" + line._1 + "  value=" + line._2)
      })
    })
    ssc.start()
    ssc.awaitTermination()
  }

}
