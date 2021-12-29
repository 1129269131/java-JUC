package com.koala.jucsenior.bashthread;

import java.util.concurrent.TimeUnit;

/**
 * day01：
 *      演示守护线程和用户线程
 * Create by koala on 2021-12-28
 */
public class DaemonDemo
{
    public static void main(String[] args)
    {
        Thread a = new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+" come in：\t"
                    +(Thread.currentThread().isDaemon() ? "守护线程":"用户线程"));
            while (true)
            {

            }
        }, "a");
        a.setDaemon(true);//设置线程为守护线程
        a.start();

        //暂停几秒钟线程
        try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { e.printStackTrace(); }

        System.out.println(Thread.currentThread().getName()+"\t"+" ----task is over");
    }
}
