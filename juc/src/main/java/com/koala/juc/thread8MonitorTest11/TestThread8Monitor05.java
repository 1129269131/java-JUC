package com.koala.juc.thread8MonitorTest11;

/**
 * Create by koala on 2020-04-02
 *
 * 4.一个静态同步方法，一个普通同步方法，一个 Number 对象， 打印？ //随机
 *
 */
public class TestThread8Monitor05 {

    public static void main(String[] args) {
        Number5 number = new Number5();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Number5.getOne();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                number.getTwo();
            }
        }).start();

    }

}

class Number5 {

    public static synchronized void getOne() {
        System.out.println("one");
    }

    public synchronized void getTwo() {
        System.out.println("two");
    }

    public void getThree() {
        System.out.println("three");
    }

}