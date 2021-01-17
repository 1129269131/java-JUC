package com.koala.juc.thread8MonitorTest11;

/**
 * Create by koala on 2020-04-02
 *
 * 2.新增 Thread.sleep() 给 getOne() ，打印? //one two
 *
 */
public class TestThread8Monitor02 {

    public static void main(String[] args) {
        Number2 number = new Number2();

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
    }

}

class Number2{

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

}