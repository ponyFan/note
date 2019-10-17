package com.test.shell;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.*;
import java.math.BigDecimal;

public class TestRuntime {

    public static void main(String[] args) throws IOException {
        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(args[0]));
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(args[1]));
        int len;
        long bedtime=System.currentTimeMillis();
        while((len=inputStream.read())!=-1) {
            outputStream.write(len);
        }
        long endtime=System.currentTimeMillis();
        System.out.println("运行时间为："+(endtime-bedtime)+"毫秒");
        inputStream.close();
        outputStream.close();
        System.out.println("正常运行");
        String[] path = new String[]{"172.22.6.20:/root/fzl","172.22.6.21:/root/fzl"};
        for (int i =0 ;i< 2;i++){
            executeLinuxCmd("scp " + args[1] + " " + path[i]);
        }
        System.out.println("file size is : " + getFormatSize(new File(args[0]).length()));
        System.out.println("file in md5 is : " + DigestUtils.md5Hex(new FileInputStream(args[0])));
        System.out.println("file out md5 is : " + DigestUtils.md5Hex(new FileInputStream(args[1])));
    }

    /**
     * runtime方式执行cmd
     * @param cmd
     * @return
     */
    public static String executeLinuxCmd(String cmd) {
        System.out.println("got cmd job : " + cmd);
        Runtime run = Runtime.getRuntime();
        try {
            Process process = run.exec(cmd);
            InputStream in = process.getInputStream();
            BufferedReader bs = new BufferedReader(new InputStreamReader(in));
            StringBuffer out = new StringBuffer();
            byte[] b = new byte[8192];
            for (int n; (n = in.read(b)) != -1;) {
                out.append(new String(b, 0, n));
            }
            System.out.println("job result [" + out.toString() + "]");
            in.close();
            process.destroy();
            return out.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getFormatSize(double size) {
        double kiloByte = size/1024;
        if(kiloByte < 1) {
            return size + "Byte(s)";
        }

        double megaByte = kiloByte/1024;
        if(megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte/1024;
        if(gigaByte < 1) {
            BigDecimal result2  = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte/1024;
        if(teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }
}
