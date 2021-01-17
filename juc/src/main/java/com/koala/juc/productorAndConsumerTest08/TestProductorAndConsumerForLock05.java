package com.koala.juc.productorAndConsumerTest08;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Create by koala on 2020-04-01
 *
 * 使用 jdk5 的 lock 及 Condition 完成线程通信以及等待唤醒机制以及线程同步
 *
 */
public class TestProductorAndConsumerForLock05 {

    public static void main(String[] args) {
        Clerk5 clerk = new Clerk5();

        Productor5 pro = new Productor5(clerk);
        Consumer5 cus = new Consumer5(clerk);

        new Thread(pro, "生产者 A").start();
        new Thread(cus, "消费者 B").start();

        new Thread(pro, "生产者 C").start();
        new Thread(cus, "消费者 D").start();
    }

}

//店员
class Clerk5 {
    private int product = 0;

    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    //进货
    public void get() {
        lock.lock();

        try {
            while (product >= 1) {//为了避免虚假唤醒问题，应该总是使用在循环中
                System.out.println("产品已满！");

                try {
                    condition.await();
                } catch (InterruptedException e) {
                }

            }
            System.out.println(Thread.currentThread().getName() + " : " + ++product);
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    //卖货
    public void sale() {
        lock.lock();

        try {
            while (product <= 0) {
                System.out.println("缺货！");

                try {
                    condition.await();
                } catch (InterruptedException e) {
                }
            }
            System.out.println(Thread.currentThread().getName() + " : " + --product);
            condition.signalAll();
        } finally {
            lock.unlock();
        }

    }
}

//生产者
class Productor5 implements Runnable {
    private Clerk5 clerk;

    public Productor5(Clerk5 clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
            }

            clerk.get();
        }
    }
}

//消费者
class Consumer5 implements Runnable {
    private Clerk5 clerk;

    public Consumer5(Clerk5 clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            clerk.sale();
        }
    }
}