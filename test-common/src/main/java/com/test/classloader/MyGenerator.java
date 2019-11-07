package com.test.classloader;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.lang.reflect.Method;

/**
 * 字节码动态生成class文件 javassist
 * @author zelei.fan
 * @date 2019/11/7 13:45
 */
public class MyGenerator {

    public static void main(String[] args) throws Exception {
        //动态生成class类
        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.makeClass("com.test.MyClass");
        CtMethod method = CtMethod.make("public void code(String value){}", ctClass);
        method.insertBefore("System.out.println(\"set value is \" + $1);");
        ctClass.addMethod(method);
        ctClass.writeFile("E:\\新建文件夹");

        //自定义加载器，加载动态生成的class，并调用其方法
        MyClassLoader loader = new MyClassLoader();
        Class<?> aClass = loader.loadClass("E:\\新建文件夹\\com\\test\\MyClass.class");
        Object o = aClass.newInstance();
        Method method1 = aClass.getMethod("code", String.class);
        method1.invoke(o, "rrrrr");
    }
}
