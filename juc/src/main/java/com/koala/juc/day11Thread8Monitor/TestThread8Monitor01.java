package com.koala.juc.day11Thread8Monitor;

/**
 * Create by koala on 2020-04-02
 *
 * 题目： 判断打印的 "one" or "two" ?
 *
 * 1.两个普通同步方法，两个线程，标准打印，打印？//one two
 *
 */
public class TestThread8Monitor01 {

    public static void main(String[] args) {
        Number number = new Number();

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

class Number{

    // Monitor：this
    public synchronized void getOne(){
        System.out.println("one");
    }

    // Monitor：this
    public synchronized void getTwo(){
        System.out.println("two");
    }

}