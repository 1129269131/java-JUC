package com.koala.jucsenior.objecthead;

import org.openjdk.jol.info.ClassLayout;

/**
 * day31：
 *      对象布局说明和压缩指针
 *
 *  尾巴参数：
 *      一、idea工具下面的Terminal（命令窗口）执行：java -XX:+PrintCommandLineFlags -version
 *
 *      二、
 *          1、Edit Configurations... --》VM options --》设置参数：-XX:-UseCompressedClassPointers
 *          2、执行看设置前与设置后的结果
 *
 * Create by koala on 2022-01-12
 */
public class ObjectHeadDemo
{
    public static void main(String[] args)
    {
        Object object = new Object();

        //引入了JOL，直接使用
        System.out.println(ClassLayout.parseInstance(object).toPrintable());
    }
}

