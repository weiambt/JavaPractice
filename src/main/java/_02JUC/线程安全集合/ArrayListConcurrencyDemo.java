package _02JUC.线程安全集合;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class ArrayListConcurrencyDemo {
    static void test1() throws InterruptedException {
        Vector<Integer> vector = new Vector<>();
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                vector.add(1);
            }
        });
        Thread thread2 = new Thread(() -> {
            for(Integer integer : vector) {
                System.out.println(integer);
            }
        });
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();




    }
    public static void main(String[] args) throws InterruptedException {
        test1();
        List<Integer> list = Collections.synchronizedList(new ArrayList<>());

    }
}

