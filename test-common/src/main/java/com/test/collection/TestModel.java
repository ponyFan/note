package com.test.collection;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * @author zelei.fan
 * @date 2019/11/8 11:20
 */
@Data
@Builder
@ToString
public class TestModel {

    private int id;

    private String name;

}
