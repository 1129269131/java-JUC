package com.koala.juc.day12ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Create by koala on 2020-04-02
 *
 * 1、线程池: 提供了一个线程队列，队列中保存着所有等待状态的线程。避免了创建与销毁的额外开销，提高了响应的速度。
 *
 * 2、线程池的体系结构
 *  java.util.concurrent.Executor: 负责线程的使用与调度的根接口
 *      |--ExecutorService 子接口: 线程池的主要接口
 *          |--ThreadPoolExecutor 线程池的实现类
 *          |--ScheduledExecutorService 子接口: 负责线程的调度
 *              |--ScheduledThreadPoolExecutor : 继承 ThreadPoolExecutor，实现 ScheduledExecutorService
 *
 * 3、工具类: Executors
 * ExecutorService newFixedThreadPool : 创建固定大小的线程池
 * ExecutorService newCachedThreadPool : 缓存线程池，线程池的数量不固定，可以根据需求自动的更改数量
 * ExecutorService newSingleThreadExecutor : 创建单个线程池，线程池中只有一个线程
 *
 * ScheduledExecutorService newScheduledThreadPool() : 创建固定大小的线程，可以延迟或定时的执行任务
 *
 */
public class TestThreadPool01 {

    public static void main(String[] args) {
        //1.创建线程池
        ExecutorService pool = Executors.newFixedThreadPool(5);

        ThreadPoolDemo tpd = new ThreadPoolDemo();

        //2.为线程池中的线程分配任务
        pool.submit(tpd);

        //3.关闭线程池
        pool.shutdown();
    }

}

class ThreadPoolDemo implements Runnable {

    private int i = 0;


    @Override
    public void run() {
        while (i <= 100) {
            System.out.println(Thread.currentThread().getName() + " : " + i++);
        }
    }
}
