package com.koala.jucsenior.lockupgrade;

import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;

/**
 * day33：
 *      synchronized锁种类及升级步骤
 *
 * Create by koala on 2022-01-13
 */
public class MyObject
{
    public static void main(String[] args)
    {
        // 1、无锁
        //noLock();

        // 2、偏锁
        //biasedLock();

        // 3、轻锁
        lightweightLock();
    }

    /**
     * 轻锁：
     *     测试：
     *         1、Edit Configurations... --》VM options --》设置参数：-XX:-UseBiasedLocking
     *         2、执行查看结果
     */
    public static void lightweightLock()
    {
        Object o = new Object();

        new Thread(() ->
        {
            synchronized (o)
            {
                System.out.println( ClassLayout.parseInstance(o).toPrintable());
            }

        },"t1").start();
    }

    /**
     * 偏锁：
     *      一、偏向锁JVM命令：
     *          linux虚拟机下执行：java -XX:+PrintFlagsInitial |grep BiasedLock*
     *
     *      二、测试：
     *          1、Edit Configurations... --》VM options --》设置参数：-XX:BiasedLockingStartupDelay=0
     *          2、执行查看结果
     */
    public static void biasedLock()
    {
        Object o = new Object();

        new Thread(() ->
        {
            synchronized (o)
            {
                System.out.println( ClassLayout.parseInstance(o).toPrintable());
            }

        },"t1").start();
    }

    // 无锁
    public static void noLock()
    {
        Object o = new Object();
        System.out.println(o.hashCode());//10进制
        System.out.println(Integer.toHexString(o.hashCode()));//16进制
        System.out.println(Integer.toBinaryString(o.hashCode()));//2进制

        //00100011111111000110001001011110
        //  100011111111000110001001011110

        System.out.println( ClassLayout.parseInstance(o).toPrintable());
    }
}
