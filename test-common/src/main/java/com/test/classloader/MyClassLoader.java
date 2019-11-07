package com.test.classloader;

import java.io.*;

/**
 * @author zelei.fan
 * @date 2019/11/7 10:14
 */
public class MyClassLoader extends ClassLoader{

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        System.out.println("start load class : " + name);
        FileInputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            in = new FileInputStream(new File(name));
            out = new ByteArrayOutputStream();
            int index = 0;
            while ((index = in.read()) != -1){
                out.write(index);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (null != out){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != in){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //读出class字节，调用classloader定义class
        byte[] bytes = out.toByteArray();
        return defineClass(null, bytes, 0, bytes.length);
    }
}
