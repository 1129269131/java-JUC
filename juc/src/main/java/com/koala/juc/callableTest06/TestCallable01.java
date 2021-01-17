package com.koala.juc.callableTest06;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Create by koala on 2020-03-31
 *
 * 一、创建执行线程的方式三: 实现 Callable 接口
 * 相较于实现 Runnable 接口的方式，方法可以有返回值，并且可以抛出异常。
 *
 * 二、执行 Callable 方式，需要 FutureTask 实现类的支持，用于接收运算结果。
 * FutureTask 是 Future 接口的实现类
 *
 */
public class TestCallable01 {

    public static void main(String[] args) {
        ThreadDemo td = new ThreadDemo();

        //1.执行 Callable 方式，需要 FutureTask 实现类的支持，用于接收运算结果
        FutureTask<Integer> result = new FutureTask<>(td);

        new Thread(result).start();

        //2.接收线程运算后的结果
        try {
            Integer sum = result.get(); //FutureTask 可用于 闭锁
            System.out.println(sum);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}

class ThreadDemo implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        int sum = 0;

        for (int i = 0; i <= 100; i++) {
            System.out.println(i);
            sum += i;
        }

        return sum;
    }
}

/*
class ThreadDemo implements Runnable{

    @Override
    public void run() {

    }

}*/
