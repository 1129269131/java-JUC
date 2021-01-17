package com.koala.juc.lockTest07;

/**
 * Create by koala on 2020-04-01
 *
 * 一、用于解决多线程安全问题的方式：
 *
 * synchronized: 隐式锁
 * 1. 同步代码块
 *
 * 2.同步方法
 *
 * jdk 1.5 后：
 * 3.同步锁 Lock
 * 注意：是一个显示锁，需要通过lock()方法上锁，必须通过unlock()方法进行释放锁
 *
 */
public class TestLock01 {

    public static void main(String[] args) {
        Ticket ticket = new Ticket();

        new Thread(ticket,"1号窗口").start();
        new Thread(ticket,"2号窗口").start();
        new Thread(ticket,"3号窗口").start();
    }

}

class Ticket implements Runnable{

    private int tick = 100;

    @Override
    public void run() {
        while (true){
            if(tick > 0){
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                }

                System.out.println(Thread.currentThread().getName() + " 完成售票，余票为：" + --tick);
            }
        }
    }
}