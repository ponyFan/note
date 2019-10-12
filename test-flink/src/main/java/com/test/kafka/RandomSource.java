package com.test.kafka;

import com.github.javafaker.Faker;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.util.Locale;

/**
 * @author zelei.fan
 * @date 2019/9/24 16:46
 */
public class RandomSource implements SourceFunction<String> {

    private volatile boolean isRunning = true;

    @Override
    public void run(SourceContext<String> sourceContext) throws Exception {
        Faker faker = new Faker(Locale.CHINA);
        while (isRunning){
            String msg = faker.address().buildingNumber() + "," +
                    faker.company().name() + "," +
                    faker.company().profession() + "," +
                    faker.address().fullAddress() + "," +
                    faker.address().state() + "," +
                    faker.app().name() + "," +
                    faker.app().version() + "," +
                    System.currentTimeMillis()/1000;
            System.out.println(msg);
            sourceContext.collect(msg);
        }
    }

    @Override
    public void cancel() {
        isRunning = false;
    }
}
