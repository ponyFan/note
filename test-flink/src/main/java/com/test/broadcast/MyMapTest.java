package com.test.broadcast;

import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.RichMapFunction;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zelei.fan
 * @date 2019/9/26 17:10
 */
public class MyMapTest extends RichMapFunction<String, String> {

    private Map<String, String> map = new HashMap<>();

    MyMapTest(Map<String, String> map){
        this.map = map;
    }

    @Override
    public String map(String s) throws Exception {
        if (StringUtils.isNotBlank(s)){
            String[] split = StringUtils.split(s, ",");
            if (null == s){
                split[0] = "default";
            }else {
                split[0] = map.get(split[0]);
            }
            return StringUtils.join(split, ",");
        }
        return "";
    }
}
