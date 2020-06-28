package com.test;

import org.apache.spark.deploy.SparkSubmit;

/**
 * @author zelei.fan
 * @date 2019/11/27 9:50
 * @description
 */
public class SparkRun {

    public static void main(String[] args) {
        String[] strings = {
                "--master", "spark://192.168.9.23:7077",
                "--deploy-mode", "client",
                "--name", "testjob",
                "--class", "com.ga.TestSpark",
                "E:\\prjs\\bd\\target\\bd-1.0-SNAPSHOT.jar"
        };
        SparkSubmit.main(strings);
    }
}
