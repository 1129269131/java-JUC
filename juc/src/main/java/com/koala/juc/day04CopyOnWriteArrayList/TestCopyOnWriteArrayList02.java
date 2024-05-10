package com.koala.juc.day04CopyOnWriteArrayList;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Create by koala on 2020-03-31
 *
 * CopyOnWriteArrayList/CopyOnWriteArraySet : "写入并复制"
 * 注意：添加操作多时，效率低，因为每次添加时都会进行复制，开销非常的大。
 *        并发迭代操作多时可以选择。
 *
 */
public class TestCopyOnWriteArrayList02 {

    public static void main(String[] args) {
        HelloThread2 ht = new HelloThread2();

        for (int i = 0; i < 10; i++) {
            new Thread(ht).start();
        }
    }

}

class HelloThread2 implements Runnable {

    //private static List<String> list = Collections.synchronizedList(new ArrayList<>());

    private static CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();

    static {
        list.add("AA");
        list.add("BB");
        list.add("CC");
    }

    @Override
    public void run() {

        Iterator<String> it = list.iterator();

        while (it.hasNext()) {
            System.out.println(it.next());

            list.add("AA");
        }
    }
}
