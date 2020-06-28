package com.test.lock;

import com.test.tt.Student;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * @author zelei.fan
 * @date 2020/6/19 9:57
 * @description
 */
public class Test {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        UnsafeTest unsafeTest = new UnsafeTest();
        unsafeTest.editByOffset();

        HashMap<String, Object> map = new HashMap<>();
    }
}
