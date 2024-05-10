package com.koala.juc.day08ProductorAndConsumer;

/**
 * Create by koala on 2020-04-01
 *
 * 解决死锁问题
 *
 */
public class TestProductorAndConsumer03 {

    public static void main(String[] args) {
        Clerk3 clerk = new Clerk3();

        Productor3 pro = new Productor3(clerk);
        Consumer3 cus = new Consumer3(clerk);

        new Thread(pro, "生产者 A").start();
        new Thread(cus, "消费者 B").start();
    }

}

//店员
class Clerk3 {
    private int product = 0;

    //进货
    public synchronized void get() {
        if (product >= 1) {
            System.out.println("产品已满！");

            try {
                this.wait();
            } catch (InterruptedException e) {
            }

        }

        System.out.println(Thread.currentThread().getName() + " : " + ++product);
        this.notifyAll();
    }

    //卖货
    public synchronized void sale() {
        if (product <= 0) {
            System.out.println("缺货！");

            try {
                this.wait();
            } catch (InterruptedException e) {
            }
        }

        System.out.println(Thread.currentThread().getName() + " : " + --product);
        this.notifyAll();

    }
}

//生产者
class Productor3 implements Runnable {
    private Clerk3 clerk;

    public Productor3(Clerk3 clerk) {
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
class Consumer3 implements Runnable {
    private Clerk3 clerk;

    public Consumer3(Clerk3 clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            clerk.sale();
        }
    }
}