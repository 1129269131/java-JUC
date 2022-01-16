package com.koala.jucsenior.locks;

import org.junit.Test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * day10：
 *      可重入锁
 *          指的是可重复可递归调用的锁，在外层使用锁之后，在内层仍然可以使用并且不发生死锁，这样的锁就叫做可重入锁。
 *          简单的来说就是：
 *              在一个synchronized修饰的方法或代码块的内部调用本类的其他synchronized修饰的方法或代码块时，是永远可以得到锁的
 *
 * Create by koala on 2022-01-06
 */
public class ReEntryLockDemo
{
    // 同步代码块
    static Object objectLock = new Object();
    @Test
    public void syncBlock()
    {
        new Thread(() -> {
            synchronized (objectLock) {// lock
                System.out.println("-----外层");
                synchronized (objectLock)
                {
                    System.out.println("-----中层");
                    synchronized (objectLock)
                    {
                        System.out.println("-----内层");
                    }
                }
            }//unlock
        },"t1").start();
    }

    // 同步方法
    public synchronized void m1()
    {
        m1();
    }
    @Test
    public void syncMethod()
    {
        new ReEntryLockDemo().m1();
    }

    // ReentrantLock
    @Test
    public void reentrantLock()
    {
        Lock lock = new ReentrantLock();

        new Thread(() -> {
            lock.lock();
            try
            {
                System.out.println(Thread.currentThread().getName()+"\t"+"-----外层");
                lock.lock();
                try
                {
                    System.out.println(Thread.currentThread().getName()+"\t"+"-----内层");
                }finally {
                    lock.unlock();// 解开/注释 此处进行测试
                }
            }finally {
                lock.unlock();
            }
        },"t1").start();

        new Thread(() -> {
            lock.lock();
            try
            {
                System.out.println("------22222");
            }finally {
                lock.unlock();
            }
        },"t2").start();
    }

}
