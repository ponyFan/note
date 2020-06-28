package com.test.lock;

import com.test.tt.Student;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author zelei.fan
 * @date 2020/6/19 15:40
 * @description
 */
public class UnsafeTest {

    /**
     * 根据偏移量修改对象某个属性值
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public void editByOffset() throws NoSuchFieldException, IllegalAccessException {
        /*unsafe类必须通过反射才能获取实例*/
        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true);
        Unsafe unsafe = (Unsafe)theUnsafe.get(null);

        /*新建一个student对象，然后获取对象中某个属性的offset*/
        Student student = Student.builder().id("001").name("tom").build();
        Field name = student.getClass().getDeclaredField("name");
        long offset = unsafe.objectFieldOffset(name);

        /*通过unsafe，根据对象属性的偏移量来将新值（jerry）替换上去*/
        unsafe.putObject(student, offset, "jerry");
        System.out.println(student);

        /*线程阻塞*/
        unsafe.park(false/*true为绝对时间，即在当前时间基础上增加多少毫秒；false为相对时间，即阻塞多少秒就写多少，单位为ns*/, 100000000000l);
    }
}
