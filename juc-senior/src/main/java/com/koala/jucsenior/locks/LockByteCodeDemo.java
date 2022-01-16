package com.koala.jucsenior.locks;

/**
 * day08：
 *      从字节码角度分析synchronized实现
 *  测试（分别解开注释）：
 *      target --》classes --》com --》koala --》jucsenior --》locks文件夹 --》右键 --》Open In--》Terminal
 *      输入反编译命令：javap -c LockByteCodeDemo.class 或者 javap -v LockByteCodeDemo.class
 *
 * Create by koala on 2022-01-05
 */
public class LockByteCodeDemo
{
    final Object object = new Object();


    public void m1()
    {
        synchronized (object){
            System.out.println("----------hello sync");
            //throw new RuntimeException("----ex");
        }
    }

    /*public synchronized void m2()
    {

    }*/

    /*public static synchronized void m2()
    {

    }*/
}
