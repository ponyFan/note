package com.test.template;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MyTask {

    @Scheduled(cron = "*/5 * * * * ?")
    private void test(){
        System.out.println(System.currentTimeMillis());
    }
}
