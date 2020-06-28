package com.test.anotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.FieldSignature;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Field;

/**
 * @author zelei.fan
 * @date 2020/1/7 10:35
 * @description 切点为注解，对注解的参数（类或者方法等）进行提前处理
 */
@Aspect
public class NotNullProcessor {

    @Before("@annotation(NotEmpty)")
    public void execute(JoinPoint joinPoint) throws IllegalAccessException {
        FieldSignature signature = (FieldSignature) joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Field field = signature.getField();
        com.test.anotation.Field annotation = field.getAnnotation(com.test.anotation.Field.class);
        String msg = annotation.msg();
        field.setAccessible(true);
        Object o = field.get(joinPoint.getThis());
    }
}
