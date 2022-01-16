package com.koala.jucsenior.lockupgrade;

class TrainTicket
{
    private int number = 50;

    Object objectLock = new Object();

    public void sale()
    {
        synchronized (objectLock)
        {
            if(number>0)
            {
                System.out.println(Thread.currentThread().getName()+"\t"+"---卖出第： "+(number--));
            }
        }
    }
}


/**
 * day32：
 *      当一段同步代码一直被同一个线程多次访问，
 *      由于只有一个线程那么该线程在后续访问时便会自动获得锁
 *
 * Create by koala on 2022-01-15
 */
public class SaleTicketDemo
{
    public static void main(String[] args)
    {
        TrainTicket trainTicket = new TrainTicket();

        new Thread(() -> { for (int i = 1; i <=55 ; i++) trainTicket.sale(); },"t1").start();
        new Thread(() -> { for (int i = 1; i <=55 ; i++) trainTicket.sale(); },"t2").start();
        new Thread(() -> { for (int i = 1; i <=55 ; i++) trainTicket.sale(); },"t3").start();
        new Thread(() -> { for (int i = 1; i <=55 ; i++) trainTicket.sale(); },"t4").start();

    }
}
