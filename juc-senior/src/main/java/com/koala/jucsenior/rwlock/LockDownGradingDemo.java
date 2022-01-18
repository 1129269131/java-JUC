package com.koala.jucsenior.rwlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * day38：
 *      锁降级
 *          锁降级：遵循获取写锁 → 再获取读锁 → 再释放写锁的次序，写锁能够降级成为读锁。
 *          如果一个线程占有了写锁，在不释放写锁的情况下，它还能占有读锁，即写锁降级为读锁。
 *
 * Create by koala on 2022-01-16
 */
public class LockDownGradingDemo
{
    //有且只有一个线程main，来验证锁降级策略要求
    public static void main(String[] args)
    {
        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();

        writeLock.lock();
        System.out.println("-------正在写入");

        readLock.lock();
        System.out.println("-------正在读取");

        writeLock.unlock();

        readLock.unlock();
    }
}
