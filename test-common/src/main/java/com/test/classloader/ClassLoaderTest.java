package com.test.classloader;

/**
 * @author zelei.fan
 * @date 2019/11/7 10:22
 */
public class ClassLoaderTest {

    public static void main(String[] args) throws Exception {
        MyClassLoader loader = new MyClassLoader();
        Class<?> aClass = loader.loadClass("F:\\myprj\\note\\test-common\\target\\classes\\com\\test\\classloader\\TestClass.class");
        TestClass o = (TestClass)aClass.newInstance();
        System.out.println(o);
    }
}
