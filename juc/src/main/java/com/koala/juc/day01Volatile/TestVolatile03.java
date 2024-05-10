package com.koala.juc.day01Volatile;

/**
 * Create by koala on 2020-03-30
 *
 * volatile 关键字：当多个线程进行操作共享数据时，可以保证内存中的数据可见
 *                  相较于 synchronized 是一种较为轻量级的同步策略
 * 注意：
 * 1. volatile 不具备“互斥性”
 * 2. volatile 不能保证变量的“原子性”
 *
 */
public class TestVolatile03 {
    public static void main(String[] args) {
        ThreadDemo03 td = new ThreadDemo03();
        new Thread(td).start();
        while (true) {
            if (td.isFlag()) {
                System.out.println("------------------");
                break;
            }
        }
    }
}

class ThreadDemo03 implements Runnable {
    private volatile boolean flag = false;

    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
        }

        flag = true;

        System.out.println("flag=" + isFlag());
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}