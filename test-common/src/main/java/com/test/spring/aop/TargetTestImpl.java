package com.test.spring.aop;

import com.test.entity.Student;
import org.springframework.stereotype.Component;

/**
 * @author zelei.fan
 * @date 2019/8/26 19:06
 */
@Component
public class TargetTestImpl {

    public String testInsert(Student student){
        return student.toString();
    }
}
