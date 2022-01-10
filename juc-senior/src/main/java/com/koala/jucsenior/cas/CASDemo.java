package com.koala.jucsenior.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * day18：
 *      CAS
 *
 * Create by koala on 2022-01-09
 */
public class CASDemo
{
    public static void main(String[] args)
    {
        AtomicInteger atomicInteger = new AtomicInteger(5);
        System.out.println(atomicInteger.get());


        System.out.println(atomicInteger.compareAndSet(5, 308)+"\t"+atomicInteger.get());

        System.out.println(atomicInteger.compareAndSet(5, 3333)+"\t"+atomicInteger.get());
    }
}
