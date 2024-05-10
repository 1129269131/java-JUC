package com.koala.juc.day12ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Create by koala on 2020-04-02
 */
public class TestThreadPool02 {

    public static void main(String[] args) {
        //1.创建线程池
        ExecutorService pool = Executors.newFixedThreadPool(5);

        ThreadPoolDemo tpd = new ThreadPoolDemo();

        //2.为线程池中的线程分配任务
        for (int i = 0; i < 10; i++) {
            pool.submit(tpd);
        }

        //3.关闭线程池
        pool.shutdown();
    }

}
