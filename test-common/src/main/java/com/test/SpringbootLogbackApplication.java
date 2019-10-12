package com.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zelei.fan
 * @date 2019/10/9 15:55
 */
@SpringBootApplication
public class SpringbootLogbackApplication {
    private final static Logger logger = LoggerFactory.getLogger(SpringbootLogbackApplication.class);

    public static void main(String[] args) {
        new Thread(()->{
            for (int i=0;i<100;i++){
                logger.debug("---test---"+i);
            }
        }).start();
        SpringApplication.run(SpringbootLogbackApplication.class, args);
    }
}
