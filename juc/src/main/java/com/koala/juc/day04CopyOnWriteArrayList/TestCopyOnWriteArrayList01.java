package com.koala.juc.day04CopyOnWriteArrayList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Create by koala on 2020-03-31
 *
 * CopyOnWriteArrayList/CopyOnWriteArraySet : "写入并复制"
 *
 */
public class TestCopyOnWriteArrayList01 {

    public static void main(String[] args) {
        HelloThread ht = new HelloThread();

        for (int i = 0; i < 10; i++) {
            new Thread(ht).start();
        }
    }

}

class HelloThread implements Runnable {

    private static List<String> list = Collections.synchronizedList(new ArrayList<>());

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
