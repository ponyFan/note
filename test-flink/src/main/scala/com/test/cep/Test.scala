package com.test.cep

import java.util.Properties

import com.test.kafka.{MessageBean, MySchema}
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment._
import org.apache.flink.streaming.connectors.kafka.{FlinkKafkaConsumer010, FlinkKafkaConsumer011}

/**
 * @author zelei.fan
 * @date 2019/9/25 17:01
 */
object Test {

  def main(args: Array[String]): Unit = {
    var env = getExecutionEnvironment
  }

  def source[topic: String]: Unit ={

  }

  case class LoginEvent(id: String, ip: String, state: String)
}
