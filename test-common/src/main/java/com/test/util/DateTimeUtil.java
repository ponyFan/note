package com.test.util;

import com.test.entity.Student;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author zelei.fan
 * @date 2019/8/26 19:58
 */
public class DateTimeUtil {

    static final String DATE = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获取当前字符串时间
     * @return
     */
    public static String getCurrentDateString(){
        SimpleDateFormat sf = new SimpleDateFormat(DATE);
        return sf.format(new Date());
    }

    /**
     * 时间戳转string
     * @param timestamp
     * @return
     */
    public static String long2String(long timestamp){
        SimpleDateFormat sf = new SimpleDateFormat(DATE);
        return sf.format(timestamp);
    }

    public static void testGC(){
        double before = System.currentTimeMillis();
        for (int i = 0; i < 100000000; i++) {
            Student student = new Student(10, 1, "sss", 3, "us");
        }
        double after = System.currentTimeMillis();
        System.out.println(after-before);
        before = System.currentTimeMillis();
        Student student = new Student();
        for (int i = 0; i < 100000000; i++) {
            student.setAge(1);
            student.setAddress("wwwwww");
            student.setGrade(3);
            student.setId(33);
            student.setName("qq");
        }
        after = System.currentTimeMillis();
        System.out.println(after-before);
    }

    public static void getYearDate() throws ParseException {
        String dateStart="2019-01-01";
        String dateEnd="2019-12-31";
        SimpleDateFormat date=new SimpleDateFormat("yyyy-MM-dd");
        long startTime = date.parse(dateStart).getTime();//start
        long endTime = date.parse(dateEnd).getTime();//end
        long day=1000*60*60*24;
        for(long i=startTime;i<=endTime;i+=day) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date(i));
            System.out.println(date.format(new Date(i)) +"  "+ (cal.get(Calendar.DAY_OF_WEEK) -1)
                    +"--"+ cal.get(Calendar.YEAR)+"--"+ (cal.get(Calendar.MONTH)+1)+"--"+ cal.get(Calendar.DATE));
        }
    }
}
