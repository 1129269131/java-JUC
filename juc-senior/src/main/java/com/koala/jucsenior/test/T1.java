package com.koala.jucsenior.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

/**
 * Create by koala on 2022-01-04
 */

@Slf4j
public class T1
{

    //day05：链式调用
    @Test
    public void m1(){
        Book book = new Book();
        book.setId(1).setPrice(23.8).setBookName("水浒传");
    }

    ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> 0);

    public void m2()
    {
        Integer value = threadLocal.get();
        ++value;
        threadLocal.set(value);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException
    {

        T1 t1 = new T1();

        new Thread(() -> {
            try {
                for (int i = 1; i <=300; i++) {
                    t1.m2();
                }
            } finally {
                //t1.threadLocal.remove();
            }
            System.out.println(t1.threadLocal.get());
        },"a").start();

        new Thread(() -> {
            try {
                for (int i = 1; i <=5; i++) {
                    t1.m2();
                }
                System.out.println(t1.threadLocal.get());
            } finally {
                t1.threadLocal.remove();
            }
        },"b").start();

    }
}
