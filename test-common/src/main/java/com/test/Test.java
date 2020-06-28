package com.test;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author zelei.fan
 * @date 2019/9/20 13:44
 */
public class Test {

    public static void main(String[] args) throws ParseException {

        String[] split = StringUtils.split("q=1,e=23,f=4,s=5", ",");
        System.out.println(split.toString());


        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sf.format(1569842670000L));
        System.out.println(sf.parse("2019-09-30 19:24:30").getTime());

        /*while (true){
            System.out.println(System.currentTimeMillis());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
    }
}
