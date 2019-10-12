package com.test.tt;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * @author zelei.fan
 * @date 2019/9/19 11:53
 */
@Data
@ToString
@Builder
public class School {

    private Student student;

    private Teacher teacher;
}
