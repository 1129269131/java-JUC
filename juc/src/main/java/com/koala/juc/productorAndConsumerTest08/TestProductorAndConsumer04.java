package com.koala.juc.productorAndConsumerTest08;

/**
 * Create by koala on 2020-04-01
 *
 * 解决虚假唤醒问题
 *
 */
public class TestProductorAndConsumer04 {

    public static void main(String[] args) {
        Clerk4 clerk = new Clerk4();

        Productor4 pro = new Productor4(clerk);
        Consumer4 cus = new Consumer4(clerk);

        new Thread(pro, "生产者 A").start();
        new Thread(cus, "消费者 B").start();

        new Thread(pro, "生产者 C").start();
        new Thread(cus, "消费者 D").start();
    }

}

//店员
class Clerk4 {
    private int product = 0;

    //进货
    public synchronized void get() {
        while (product >= 1) {//为了避免虚假唤醒问题，应该总是使用在循环中
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
        while (product <= 0) {
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
class Productor4 implements Runnable {
    private Clerk4 clerk;

    public Productor4(Clerk4 clerk) {
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
class Consumer4 implements Runnable {
    private Clerk4 clerk;

    public Consumer4(Clerk4 clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            clerk.sale();
        }
    }
}