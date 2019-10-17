package com.test.shell;

import com.jcraft.jsch.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * JSch方式
 * @author zelei.fan
 * @date 2019/9/17 10:50
 */
public class Testssh {

    private static Session session;

    static {
        JSch jsch = new JSch();
        try {
            session = jsch.getSession("root", "172.22.6.20", 22);
            session.setPassword("transwarpnj");
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
        } catch (JSchException e) {
            e.printStackTrace();
            System.out.println("error get connection");
        }
    }

    public static void main(String[] args) throws JSchException, InterruptedException {
        exe("mkdir /root/t1 | cp /root/fzl/access.log /root/t1");
        /*exe("diff /root/fzl/test.log /root/fzl/test1.log");*/
        /*try {
            ftp();
        } catch (SftpException | IOException e) {
            e.printStackTrace();
        }*/
    }

    /**
     * JSch方式执行命令
     * @param cmd
     */
    public static void exe(String cmd){
        ChannelExec exec = null;
        try {
            exec = (ChannelExec)session.openChannel("exec");
            exec.setCommand(cmd);
            exec.setInputStream(null);
            exec.setErrStream(System.err);
            exec.setOutputStream(System.out);
            exec.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(exec.getInputStream()));
            String str = null;
            String buf = null;
            while ((buf = reader.readLine()) != null) {
                /*System.out.println("aaaaaaaaa" + buf);*/
                str = str + buf +"\n";
            }
            System.out.println("str is : " + str);
        } catch (JSchException | IOException e) {
            e.printStackTrace();
        }
        while(!exec.isClosed()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        exec.disconnect();
        session.disconnect();
    }

    /**
     * JSch方式上传文件
     * @throws JSchException
     * @throws SftpException
     * @throws IOException
     */
    public static void ftp() throws JSchException, SftpException, IOException {
        ChannelSftp sftpChannel=(ChannelSftp)session.openChannel("sftp");
        sftpChannel.connect();
        /*生成临时文件，并且复制*/
        File file = File.createTempFile("temp", Long.toString(System.nanoTime()));
        Files.copy(Paths.get("C:\\Users\\fanzelei\\Downloads\\access.log"), new FileOutputStream(file.getAbsolutePath()));
        sftpChannel.put(file.getAbsolutePath(), "/root/fzl");
        sftpChannel.disconnect();
        session.disconnect();
    }
}
