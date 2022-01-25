package com.koala.jucsenior.tl;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * day30：
 *      ThreadLocal解决SimpleDateFormat线程不安全问题
 * Create by koala on 2022-01-11
 */
public class ThreadLocalDateUtils
{
    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static Date parse(String stringDate) throws ParseException
    {
        return sdf.parse(stringDate);
    }

    public static void main(String[] args) throws ParseException {
        //m1();//1
        //m2();//2
        //m3();//3
        m4();//4
    }

    // 问题：非线程安全的SimpleDateFormat
    public static void m1() throws ParseException
    {
        for (int i = 1; i <=10; i++) {
            new Thread(() -> {
                try {
                    System.out.println(ThreadLocalDateUtils.parse("2011-11-11 11:11:11"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        }
    }

    // 解决1：将SimpleDateFormat定义成局部变量。
    public static void m2() throws ParseException
    {
        for (int i = 1; i <=10; i++) {
            new Thread(() -> {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    System.out.println(sdf.parse("2011-11-11 11:11:11"));
                    sdf = null;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        }
    }

    //2  ThreadLocal可以确保每个线程都可以得到各自单独的一个SimpleDateFormat的对象，那么自然也就不存在竞争问题了。
    public static final ThreadLocal<SimpleDateFormat> sdfThreadLocal = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    public static final Date parseByThreadLocal(String stringDate) throws ParseException
    {
        return sdfThreadLocal.get().parse(stringDate);
    }

    // 解决2：ThreadLocal
    public static void m3() throws ParseException
    {
        for (int i = 1; i <=10; i++) {
            new Thread(() -> {
                try {
                    System.out.println(ThreadLocalDateUtils.parseByThreadLocal("2011-11-11 11:11:11"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }finally {
                    sdfThreadLocal.remove();
                }
            },String.valueOf(i)).start();
        }
    }


    /*说明：如果是 JDK8 的应用，可以使用 Instant 代替 Date，LocalDateTime 代替 Calendar，
    DateTimeFormatter 代替 SimpleDateFormat，官方给出的解释：simple beautiful strong immutable
    thread-safe。*/
    public static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String format(LocalDateTime localDateTime)
    {
        return DATE_TIME_FORMAT.format(localDateTime);
    }

    public static LocalDateTime parsLocalDateTime(String dateString)
    {
        return LocalDateTime.parse(dateString,DATE_TIME_FORMAT);
    }

    // 解决3：DateTimeFormatter 代替 SimpleDateFormat
    public static void m4(){
        for (int i = 1; i <=10; i++) {
            new Thread(() -> {
                System.out.println(ThreadLocalDateUtils.parsLocalDateTime("2011-11-11 11:11:11"));
            },String.valueOf(i)).start();
        }
    }
}
