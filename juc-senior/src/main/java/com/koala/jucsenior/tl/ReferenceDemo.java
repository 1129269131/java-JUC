package com.koala.jucsenior.tl;

import org.junit.Test;

import java.lang.ref.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

class MyObject {
    //一般这个方法工作中不用，此处为了讲解gc，给学生们演示
    @Override
    protected void finalize() throws Throwable {
        System.out.println("------------- gc ,finalize() invoked");
    }
}

/**
 * day31：
 *      强引用、软引用、弱引用、虚引用
 * Create by koala on 2022-01-12
 */
public class ReferenceDemo
{
    public static void main(String[] args) {
        //1、强引用
        //strongReference();

        //2、软引用
        //softReference();

        //3、弱引用
        //weakReference();

        //4、虚引用
        phantomReference();
    }

    // 强引用
    public static void strongReference()
    {
        //Edit Configurations... --》VM options --》设置参数为空

        MyObject myObject = new MyObject();//默认，强引用,死了都不放手
        System.out.println("gc before: "+myObject);

        myObject = null;// 注释/解开 该行进行测试
        System.gc();//手动挡的方式开启Gc回收。
        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }

        System.out.println("gc after: "+myObject);
    }

    // 软引用：分别测试内存够用与内存不够用的情况
    public static void softReference()
    {
        SoftReference<MyObject> softReference = new SoftReference<>(new MyObject());//软引用

        //Edit Configurations... --》VM options --》设置参数为空
        //内存够用
        /*System.out.println("gc before内存够用: "+softReference);

        System.gc();//手动挡的方式开启Gc回收。
        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }

        System.out.println("gc after内存够用: "+softReference);*/


        //Edit Configurations... --》VM options --》设置参数-Xms10m -Xmx10m
        //内存不够用
        System.out.println("gc before: "+softReference);
        try
        {
            byte[] bytes = new byte[9 * 1024 * 1024];
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println("-----gc after内存不够: "+softReference.get());
        }
    }

    //弱引用
    public static void weakReference() {
        //Edit Configurations... --》VM options --》设置参数为空

        WeakReference<MyObject> weakReference = new WeakReference(new MyObject());
        System.out.println("gc before: "+weakReference.get());

        System.gc();//手动挡的方式开启Gc回收。
        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }

        System.out.println("gc after: "+weakReference.get());
    }

    //虚引用
    public static void phantomReference() {
        //Edit Configurations... --》VM options --》设置参数-Xms15m -Xmx15m
        ReferenceQueue<MyObject> referenceQueue = new ReferenceQueue<>();
        PhantomReference<MyObject> phantomReference = new PhantomReference<>(new MyObject(),referenceQueue);
        System.out.println(phantomReference.get());

        List<byte[]> list = new ArrayList<>();

        new Thread(() -> {
            while (true)
            {
                list.add(new byte[1 * 1024 * 1024]);
                try { TimeUnit.MILLISECONDS.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
                System.out.println(phantomReference.get());
            }
        },"t1").start();

        new Thread(() -> {
            while(true)
            {
                Reference<? extends MyObject> poll = referenceQueue.poll();
                if (poll != null) {
                    System.out.println("------有虚对象进入了队列");
                }
            }
        },"t2").start();

        //暂停几秒钟线程
        try { TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) { e.printStackTrace(); }

    }

}
