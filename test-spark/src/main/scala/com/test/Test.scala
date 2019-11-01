package com.test

import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010._

/**
 * ./spark-submit
 * --master yarn
 * --deploy-mode client
 * --name test-spark
 * --jars /root/fzl/spark-yarn-jar/spark-streaming-kafka-0-10_2.11-2.4.3.jar,/root/fzl/spark-yarn-jar/kafka-clients-0.10.2.0.jar,/root/fzl/spark-yarn-jar/mysql-connector-java-8.0.13.jar
 * --class com.test.Test
 * /root/fzl/test-spark-1.0-SNAPSHOT.jar test001
 * @author zelei.fan
 * @date 2019/9/19 9:42
 */
object Test {

  def main(args: Array[String]): Unit = {
    val ssc = new StreamingContext(new SparkConf(), Seconds(5))
    val topics = Array(args(0)).toSet
    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "172.22.6.19:9092,172.22.6.20:9092,172.22.6.21:9092",
        "auto.offset.reset" -> "earliest",
        "key.deserializer" -> classOf[StringDeserializer],
        "value.deserializer" -> classOf[StringDeserializer],
        "group.id" -> "use_a_separate_group_id_for_each_stream",
        "auto.offset.reset" -> "latest"
    )
    val stream = KafkaUtils.createDirectStream[String, String](
      ssc,
      PreferConsistent,
      Subscribe[String, String](topics, kafkaParams)
    )
    stream.map(record => {
      Class.forName("com.mysql.cj.jdbc.Driver")
    }).print()
    ssc.start()
    ssc.awaitTermination()
  }

}
