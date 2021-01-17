package com.koala.juc.scheduledThreadPoolTest13;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

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
 */
public class TestScheduledThreadPool01 {

    public static void main(String[] args) throws Exception {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(5);

        for (int i = 0; i < 5; i++) {
            ScheduledFuture result = pool.schedule(new Callable<Object>() {
                @Override
                public Integer call() throws Exception{
                    int num = new Random().nextInt(100);//生成随机数
                    System.out.println(Thread.currentThread().getName() + " : " + num);
                    return num;
                }
            }, 3, TimeUnit.SECONDS);

            System.out.println(result.get());
        }

        pool.shutdown();

    }

}
