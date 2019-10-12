package com.test.tt;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * @author zelei.fan
 * @date 2019/9/19 11:54
 */
@Data
@ToString
@Builder
public class Student {

    private String id;

    private String name;
}
