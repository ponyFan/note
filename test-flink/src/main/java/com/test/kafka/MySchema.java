package com.test.kafka;

import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;

import java.io.IOException;

/**
 * @author zelei.fan
 * @date 2019/9/23 19:45
 */
public class MySchema implements DeserializationSchema<MessageBean> {
    @Override
    public MessageBean deserialize(byte[] bytes) throws IOException {
        String s = new String(bytes);
        try {
            String[] split = StringUtils.split(s, ",");
            return new MessageBean(split[0], split[1], split[2], split[3], split[4], split[5], split[6], split[7]);
        }catch (Exception e){
            System.out.println("message is not format message : " + s);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean isEndOfStream(MessageBean o) {
        return false;
    }

    @Override
    public TypeInformation getProducedType() {
        return TypeInformation.of(MessageBean.class);
    }
}
