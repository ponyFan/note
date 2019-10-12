package com.test.cep

import org.apache.flink.api.scala.{ExecutionEnvironment, _}
import org.apache.flink.cep.scala.CEP
import org.apache.flink.cep.scala.pattern.Pattern

/**
 * @author zelei.fan
 * @date 2019/9/25 17:01
 */
object Test {
  def main(args: Array[String]): Unit = {

  }

  case class LoginEvent(id: String, ip: String, state: String)
}
