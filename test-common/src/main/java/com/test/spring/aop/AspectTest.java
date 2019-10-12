package com.test.spring.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author zelei.fan
 * @date 2019/8/26 18:08
 * @description aop切面示例
 */
@Aspect
@Component
public class AspectTest {

    @Before("execution(* com.test.spring.aop.*Impl.*Insert*(..)) && args(t)")
    public <T> void before(T t) throws InvocationTargetException, IllegalAccessException {
        Class<?> aClass = t.getClass();
        PropertyDescriptor descriptor = BeanUtils.getPropertyDescriptor(aClass, "age");
        Method writeMethod = descriptor.getWriteMethod();
        writeMethod.invoke(t, 333);
    }
}
