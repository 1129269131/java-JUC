package com.koala.juc.day02Atomic;

/**
 * Create by koala on 2020-03-30
 *
 * i++ 的原子性问题：i++的操作实际上分为三个步骤“读-改-写”
 *      int i = 10;
 *      i = i++;//10
 *
 *      int temp = i;
 *      i = i + 1;
 *      i = temp;
 *
 */
public class TestAtomicDemo02 {

    public static void main(String[] args) {
        AtomicDemo2 ad = new AtomicDemo2();

        for (int i = 0; i < 10; i++) {
            new Thread(ad).start();
        }
    }

}

class AtomicDemo2 implements Runnable {

    private volatile int serialNumber = 0;

    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
        }

        System.out.println(getSerialNumber());
    }

    public int getSerialNumber() {
        return serialNumber++;
    }
}
