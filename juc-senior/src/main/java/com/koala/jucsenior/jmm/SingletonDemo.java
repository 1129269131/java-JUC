package com.koala.jucsenior.jmm;

/**
 * day17：
 *      采用静态内部类的方式实现单例模式
 *
 * Create by koala on 2022-01-09
 */
public class SingletonDemo
{
    private SingletonDemo() { }

    private static class SingletonDemoHandler
    {
        private static SingletonDemo instance = new SingletonDemo();
    }

    public static SingletonDemo getInstance()
    {
        return SingletonDemoHandler.instance;
    }
}
