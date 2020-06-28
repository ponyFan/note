package com.test.io.download;

import com.test.common.MyException;

import java.io.*;

/**
 * @author zelei.fan
 * @date 2020/2/25 11:29
 * @description 文件断点续传
 */
public class FileDownload {

    /**
     * 文件复制
     */
   public static void fileCopy(){
       File source = new File("D:\\fzl\\ideaIU-2019.2.exe");
       File target = new File("E:\\root\\tmp\\idea.exe");
       FileInputStream in = null;
       FileOutputStream out = null;
       try {
           in = new FileInputStream(source);
           out = new FileOutputStream(target);
           byte[] bytes = new byte[1024];
           while (in.read(bytes) != -1){
               out.write(bytes);
           }
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }finally {
           if (null != in) {
               try {
                   in.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
           if (null != out) {
               try {
                   out.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
       }
   }

    /**
     * 文件断连复制
     */
   public static void fileCopyBreakEven(){
       File source = new File("E:\\root\\tmp\\2019-12-30\\20191206143827_00a44346.png");
       File target = new File("E:\\root\\tmp\\img.png");
       FileInputStream in = null;
       FileOutputStream out = null;
       int position = 0;
       try {
           in = new FileInputStream(source);
           out = new FileOutputStream(target);
           byte[] bytes = new byte[1];
           while (in.read(bytes) != -1){
                out.write(bytes);
                /*此处设置传输到第10个字节时，出现断在网络异常，传输停止*/
                if (target.length() == 10){
                    position = 10;
                    throw new MyException();
                }
           }
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       } catch (MyException e) {
           e.printStackTrace();
           try {
               /*记录文件传输过程中异常时，字节读取的指针位置，持久化到文件中；以便进程重启后读取该指针，从原来位置继续读取字节*/
               OutputStreamWriter streamWriter = new OutputStreamWriter(new FileOutputStream("E:\\root\\tmp\\download.index.txt"));
               BufferedWriter writer = new BufferedWriter(streamWriter);
               writer.write(String.valueOf(position));
               writer.flush();
               streamWriter.close();
               writer.close();
           } catch (IOException ex) {
               ex.printStackTrace();
           }
           /*keepGoing(source, target, position);*/
       }
   }

    /**
     * 断点续传2，模拟进程挂掉后，重启时继续读取；
     * 主要实现就是异常时将当前的position记录到文件中做持久化，下次下载前先读取文件中的position
     */
   public static void fileCopyBreakEven2(){
       /*下载文件前线读取指针文件，如果文件有指针数据则代表上次有未下载完的文件，继续从该点下载*/
       File file = new File("E:\\root\\tmp\\download.index.txt");
       int position = 0;
       if (file.exists()){
           try {
               BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
               String s = bufferedReader.readLine();
               position = Integer.valueOf(s);
           } catch (FileNotFoundException e) {
               e.printStackTrace();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
       if (position != 0){
            keepGoing(new File("E:\\root\\tmp\\2019-12-30\\20191206143827_00a44346.png"), new File("E:\\root\\tmp\\img.png"), position);
       }else {
           fileCopyBreakEven();
       }
   }

    /**
     * 断点续传，主要原理就是记录上次读取的指针位置，续传时从该位置继续往后读
     * @param source 源
     * @param target 目标
     * @param position 续传的指针位置
     */
   private static void keepGoing(File source, File target, int position){
       /*模拟网络断线时间*/
       try {
           Thread.sleep(1*60*1000);
       } catch (InterruptedException e) {
           e.printStackTrace();
       }
       RandomAccessFile read = null;
       RandomAccessFile write = null;
       try {
           read = new RandomAccessFile(source, "rw");
           write = new RandomAccessFile(target, "rw");
           read.seek(position);
           write.seek(position);
           byte[] bytes = new byte[1];
           while (read.read(bytes) != -1){
               write.write(bytes);
           }
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }
   }

    public static void main(String[] args) {
       /*普通复制*/
       /*fileCopy();*/
        /*网络异常断点复制*/
        /*fileCopyBreakEven();*/
        /*进程挂掉后断点复制*/
        fileCopyBreakEven2();
    }
}
