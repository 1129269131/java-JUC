package com.koala.jucsenior.rwlock;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class MyResource1
{
    Map<String,String> map = new HashMap<>();
    //=====ReentrantLock 等价于 =====synchronized
    Lock lock = new ReentrantLock();

    public void write(String key,String value)
    {
        lock.lock();
        try
        {
            System.out.println(Thread.currentThread().getName()+"\t"+"---正在写入");
            map.put(key,value);
            try { TimeUnit.MILLISECONDS.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
            System.out.println(Thread.currentThread().getName()+"\t"+"---完成写入");
        }finally {
            lock.unlock();
        }
    }

    public void read(String key)
    {
        lock.lock();
        try
        {
            System.out.println(Thread.currentThread().getName()+"\t"+"---正在读取");
            String result = map.get(key);
            //暂停几秒钟线程
            try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { e.printStackTrace(); }
            System.out.println(Thread.currentThread().getName()+"\t"+"---完成读取result： "+result);
        }finally {
            lock.unlock();
        }
    }

}

class MyResource2
{
    Map<String,String> map = new HashMap<>();
    //=====ReentrantLock 等价于 =====synchronized
    Lock lock = new ReentrantLock();
    //=====ReentrantReadWriteLock 一体两面，读写互斥，读读共享
    ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    public void write(String key,String value)
    {
        rwLock.writeLock().lock();
        try
        {
            System.out.println(Thread.currentThread().getName()+"\t"+"---正在写入");
            map.put(key,value);
            try { TimeUnit.MILLISECONDS.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
            System.out.println(Thread.currentThread().getName()+"\t"+"---完成写入");
        }finally {
            rwLock.writeLock().unlock();
        }
    }

    public void read(String key)
    {
        rwLock.readLock().lock();
        try
        {
            System.out.println(Thread.currentThread().getName()+"\t"+"---正在读取");
            String result = map.get(key);
            //暂停几秒钟线程
            try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { e.printStackTrace(); }
            System.out.println(Thread.currentThread().getName()+"\t"+"---完成读取result： "+result);
        }finally {
            rwLock.readLock().unlock();
        }
    }

}

/**
 * day37：
 *      ReentrantLock、ReentrantReadWriteLock
 *
 * Create by koala on 2022-01-15
 */
public class ReentrantReadWriteLockDemo
{
    // ReentrantLock
    public static void main(String[] args)
    {
        MyResource1 myResource = new MyResource1();

        for (int i = 1; i <=10; i++) {
            int finalI = i;
            new Thread(() -> {
                myResource.write(finalI +"", finalI +"");
            },String.valueOf(i)).start();
        }

        for (int i = 1; i <=10; i++) {
            int finalI = i;
            new Thread(() -> {
                myResource.read(finalI +"");
            },String.valueOf(i)).start();
        }

    }

    // ReentrantReadWriteLock
    /*public static void main(String[] args)
    {
        MyResource2 myResource = new MyResource2();

        for (int i = 1; i <=10; i++) {
            int finalI = i;
            new Thread(() -> {
                myResource.write(finalI +"", finalI +"");
            },String.valueOf(i)).start();
        }

        for (int i = 1; i <=10; i++) {
            int finalI = i;
            new Thread(() -> {
                myResource.read(finalI +"");
            },String.valueOf(i)).start();
        }

    }*/
}
