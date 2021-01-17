package com.koala.juc.thread8MonitorTest11;

/**
 * Create by koala on 2020-04-02
 *
 * 该方法存在问题，无需关注
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

        /*new Thread(new Runnable() {
            @Override
            public void run() {
                number.getThree();
            }
        }).start();*/
    }

}

class Number5 {

    public static synchronized void getOne() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
        }

        System.out.println("one");
    }

    public synchronized void getTwo() {
        System.out.println("two");
    }

    public void getThree() {
        System.out.println("three");
    }

}