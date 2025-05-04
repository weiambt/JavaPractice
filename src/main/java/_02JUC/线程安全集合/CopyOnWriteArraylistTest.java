package _02JUC.线程安全集合;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArraylistTest {

    // 并发读写报错
    // "Thread-1" java.util.ConcurrentModificationException
    static void test1() throws InterruptedException {
        ArrayList<Integer> list = new ArrayList<>();
        // 线程1：不断添加元素
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                list.add(i);
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // 线程2：遍历列表
        Thread thread2 = new Thread(() -> {
            for (Integer num : list) {
                System.out.println(num);
            }
        });

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
//        System.out.println(list.size());
    }

    public static void main(String[] args) throws InterruptedException {
        test1();
//        test2();


    }
}
