package com.koala.juc.thread8MonitorTest11;

/**
 * Create by koala on 2020-04-02
 *
 * 4.两个普通同步方法，两个 Number 对象， 打印？ //随机
 *
 */
public class TestThread8Monitor04 {

    public static void main(String[] args) {
        Number4 number = new Number4();
        Number4 number2 = new Number4();

        new Thread(new Runnable() {
            @Override
            public void run() {
                number.getOne();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                //number.getTwo();
                number2.getTwo();
            }
        }).start();

    }

}

class Number4 {

    public synchronized void getOne() {
        System.out.println("one");
    }

    public synchronized void getTwo() {
        System.out.println("two");
    }

}