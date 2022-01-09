package com.koala.jucsenior.jmm;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * day14：
 *      内存可见性
 *
 * Create by koala on 2022-01-08
 */
public class VolatileSeeDemo
{
    boolean flag = true;       //不加volatile，没有可见性
    //volatile boolean flag = true;       //加了volatile，保证可见性

    @Test
    public void main()
    {
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+"\t"+"---come in");
            while(flag)
            {
                new Integer(308);
            }
            System.out.println("t1 over");
        },"t1").start();

        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }

        new Thread(() -> {
            flag = false;
        },"t2").start();
    }
}
