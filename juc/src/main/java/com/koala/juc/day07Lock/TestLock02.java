package com.koala.juc.day07Lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Create by koala on 2020-04-01
 */
public class TestLock02 {

    public static void main(String[] args) {
        Ticket2 ticket = new Ticket2();

        new Thread(ticket,"1号窗口").start();
        new Thread(ticket,"2号窗口").start();
        new Thread(ticket,"3号窗口").start();
    }

}

class Ticket2 implements Runnable{

    private int tick = 100;

    private Lock lock = new ReentrantLock();

    @Override
    public void run() {
        while (true){

            lock.lock(); //上锁

            try {
                if(tick > 0){
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                    }

                    System.out.println(Thread.currentThread().getName() + " 完成售票，余票为：" + --tick);
                }
            } finally {
                lock.unlock(); //释放锁
            }
        }
    }
}