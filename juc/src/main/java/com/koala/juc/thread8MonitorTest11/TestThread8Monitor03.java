package com.koala.juc.thread8MonitorTest11;

/**
 * Create by koala on 2020-04-02
 *
 * 3.新增普通方法 getThree() ，打印？ //three one two
 *
 */
public class TestThread8Monitor03 {

    public static void main(String[] args) {
        Number3 number = new Number3();

        new Thread(new Runnable() {
            @Override
            public void run() {
                number.getOne();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                number.getTwo();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                number.getThree();
            }
        }).start();
    }

}

class Number3{

    public synchronized void getOne(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
        }

        System.out.println("one");
    }

    public synchronized void getTwo(){
        System.out.println("two");
    }

    public void getThree(){
        System.out.println("three");
    }

}