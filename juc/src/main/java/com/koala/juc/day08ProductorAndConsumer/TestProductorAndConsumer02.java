package com.koala.juc.day08ProductorAndConsumer;

/**
 * Create by koala on 2020-04-01
 *
 * 使用等待唤醒机制
 *
 */
public class TestProductorAndConsumer02 {

    public static void main(String[] args) {
        Clerk2 clerk = new Clerk2();

        Productor2 pro = new Productor2(clerk);
        Consumer2 cus = new Consumer2(clerk);

        new Thread(pro, "生产者 A").start();
        new Thread(cus, "消费者 B").start();
    }

}

//店员
class Clerk2 {
    private int product = 0;

    //进货
    public synchronized void get() {
        if (product >= 10) {
            System.out.println("产品已满！");

            try {
                this.wait();
            } catch (InterruptedException e) {
            }

        } else {
            System.out.println(Thread.currentThread().getName() + " : " + ++product);
            this.notifyAll();
        }
    }

    //卖货
    public synchronized void sale() {
        if (product <= 0) {
            System.out.println("缺货！");

            try {
                this.wait();
            } catch (InterruptedException e) {
            }
        } else {
            System.out.println(Thread.currentThread().getName() + " : " + --product);
            this.notifyAll();
        }
    }
}

//生产者
class Productor2 implements Runnable {
    private Clerk2 clerk;

    public Productor2(Clerk2 clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            clerk.get();
        }
    }
}

//消费者
class Consumer2 implements Runnable {
    private Clerk2 clerk;

    public Consumer2(Clerk2 clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            clerk.sale();
        }
    }
}