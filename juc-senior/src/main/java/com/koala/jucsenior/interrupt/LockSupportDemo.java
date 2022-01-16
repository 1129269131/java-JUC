package com.koala.jucsenior.interrupt;

import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * day13：
 *      线程等待唤醒机制
 *
 * Create by koala on 2022-01-08
 */
public class LockSupportDemo
{
    /**
     * Object类中的wait和notify方法实现线程等待和唤醒：
     *      1、wait和notify方法必须要在同步块或者方法里面，且成对出现使用
     *      2、先wait后notify才OK
     */
    static Object objectLock = new Object();
    @Test
    public void syncWaitNotify()
    {
        new Thread(() -> {
            //暂停几秒钟线程
            try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
            synchronized (objectLock){
                System.out.println(Thread.currentThread().getName()+"\t"+"---come in");
                try {
                    objectLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"\t"+"---被唤醒");
            }
        },"t1").start();

        //暂停几秒钟线程
        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }

        new Thread(() -> {
            synchronized (objectLock){
                objectLock.notify();
                System.out.println(Thread.currentThread().getName()+"\t"+"---发出通知");
            }
        },"t2").start();
    }

    /**
     * Condition接口中的await后signal方法实现线程的等待和唤醒：
     *      1、Condition中的线程等待和唤醒方法之前，需要先获取锁
     *      2、一定要先await后signal，不要反了
     */
    static Lock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();
    @Test
    public void lockAwaitSignal()
    {
        new Thread(() -> {
            //暂停几秒钟线程
            try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
            lock.lock();
            try
            {
                System.out.println(Thread.currentThread().getName()+"\t"+"---come in");
                condition.await();
                System.out.println(Thread.currentThread().getName()+"\t"+"---被唤醒");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        },"t1").start();

        new Thread(() -> {
            lock.lock();
            try
            {
                condition.signal();
                System.out.println(Thread.currentThread().getName()+"\t"+"---发出通知");
            }finally {
                lock.unlock();
            }
        },"t2").start();
    }

    /**
     * LockSupport类中的park等待和unpark唤醒
     */
    @Test
    public void lockSupport()
    {
        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t" + "---come in");
            LockSupport.park();
            LockSupport.park();
            System.out.println(Thread.currentThread().getName() + "\t" + "---被唤醒");
        }, "t1");
        t1.start();

        new Thread(() -> {
            LockSupport.unpark(t1);
            try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
            LockSupport.unpark(t1);
            System.out.println(Thread.currentThread().getName()+"\t"+"---发出通知");
        },"t2").start();
    }

}