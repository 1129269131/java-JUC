package com.koala.jucsenior.locks;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Ticket
{
    private int number = 50;

    private Lock lock = new ReentrantLock(); //默认用的是非公平锁，分配的平均一点，=--》公平一点
    // private Lock lock = new ReentrantLock(true); //参数为true的是公平锁

    public void sale()
    {
        lock.lock();
        try
        {
            if(number > 0)
            {
                System.out.println(Thread.currentThread().getName()+"\t 卖出第: "+(number--)+"\t 还剩下: "+number);
            }
        }finally {
            lock.unlock();
        }
    }



    /*Object objectLock = new Object();

    public void sale()
    {
        synchronized (objectLock)
        {
            if(number > 0)
            {
                System.out.println(Thread.currentThread().getName()+"\t 卖出第: "+(number--)+"\t 还剩下: "+number);
            }
        }
    }*/
}

/**
 * day09：
 *      公平锁和非公平锁
 * Create by koala on 2022-01-05
 */
public class SaleTicketDemo
{
    public static void main(String[] args)
    {
        Ticket ticket = new Ticket();

        new Thread(() -> { for (int i = 1; i <=55; i++) ticket.sale(); },"a").start();
        new Thread(() -> { for (int i = 1; i <=55; i++) ticket.sale(); },"b").start();
        new Thread(() -> { for (int i = 1; i <=55; i++) ticket.sale(); },"c").start();
        new Thread(() -> { for (int i = 1; i <=55; i++) ticket.sale(); },"d").start();
        new Thread(() -> { for (int i = 1; i <=55; i++) ticket.sale(); },"e").start();
    }
}
