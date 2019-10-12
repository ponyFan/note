package com.test.kafka;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * @author zelei.fan
 * @date 2019/9/23 20:19
 */
@Data
@ToString
@Builder
public class MessageBean {

    private String id;

    private String col1;

    private String col2;

    private String col3;

    private String col4;

    private String col5;

    private String col6;

    private String col7;
}
