package com.koala.juc.day01Volatile;

/**
 * Create by koala on 2020-03-30
 */
public class TestVolatile02 {
    public static void main(String[] args) {
        ThreadDemo td = new ThreadDemo();
        new Thread(td).start();
        while (true) {
            synchronized (td) {
                if (td.isFlag()) {
                    System.out.println("------------------");
                    break;
                }
            }
        }
    }
}