package com.koala.jucsenior.locks;

import java.util.concurrent.TimeUnit;

/**
 * day11：
 *      死锁及排查
 * 排查：
 *      1、纯命令：
 *          进入：target --》classes --》com --》koala --》jucsenior --》locks文件夹 --》右键 --》Open In--》Terminal
 *          输入命令：
 *              1.1、jps -l
 *              1.2、jstack 进程编号
 *              1.3、找到 Found 1 deadlock. 关键字即为发现死锁
 *      2、图形化：jconsole（推荐）
 *          2.1：win + R 进入运行命令
 *          2.2：输入 jconsole 执行
 *          2.3：在 Java监视和管理控制台 界面选择 com.koala.jucsenior.locks.DeadLockDemo 进行连接
 *          2.4：点击 不安全的连接
 *          2.5：选择 线程 菜单
 *          2.6：点击左下角的检测死锁
 *          2.7：如果有死锁，界面上就会显示死锁信息
 *
 * Create by koala on 2022-01-06
 */
public class DeadLockDemo
{
    static Object lockA = new Object();
    static Object lockB = new Object();

    public static void main(String[] args)
    {

        Thread a = new Thread(() -> {
            synchronized (lockA) {
                System.out.println(Thread.currentThread().getName() + "\t" + " 自己持有A锁，期待获得B锁");

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (lockB) {
                    System.out.println(Thread.currentThread().getName() + "\t 获得B锁成功");
                }
            }
        }, "a");
        a.start();

        new Thread(() -> {
            synchronized (lockB)
            {
                System.out.println(Thread.currentThread().getName()+"\t"+" 自己持有B锁，期待获得A锁");

                try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }

                synchronized (lockA)
                {
                    System.out.println(Thread.currentThread().getName()+"\t 获得A锁成功");
                }
            }
        },"b").start();

    }
}
