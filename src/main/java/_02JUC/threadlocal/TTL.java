package _02JUC.threadlocal;

/*
    阿里的开源组件，解决threadlocal无法集成线程池使用的问题
 */

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.threadpool.TtlExecutors;

import java.util.concurrent.*;

public class TTL {
    static InheritableThreadLocal<Integer> inheritable_threadlocal = new InheritableThreadLocal<>();

    //inheritable_threadlocal可以传递到线程池
    static void test1(){
        inheritable_threadlocal.set(1);
        ThreadPoolExecutor pool = new ThreadPoolExecutor(1,1,10, TimeUnit.DAYS,new ArrayBlockingQueue<>(10));
        pool.execute(()->{
            Integer res = inheritable_threadlocal.get();
            System.out.println(res);
        });
        pool.shutdown();
    }

    //todo inheritable_threadlocal的问题所在
    static void test2(){
        inheritable_threadlocal.set(2);
        ThreadPoolExecutor pool = new ThreadPoolExecutor(1,1,10, TimeUnit.DAYS,new ArrayBlockingQueue<>(10));

        pool.execute(()->{
            Integer res = inheritable_threadlocal.get();
            System.out.println(res);
        });

        // todo 此时父线程更新了，但是线程池中的子线程感知不到
        inheritable_threadlocal.set(3);
        pool.execute(()->{
            Integer res = inheritable_threadlocal.get();
            System.out.println(res); // 输出2，但是其实应该输出3
        });
        pool.shutdown();
    }

    // 使用阿里巴巴开源的解决方案：TransmittableThreadLocal
    static TransmittableThreadLocal<Integer> transmittable_threadlocal = new TransmittableThreadLocal<>();
    static void test3(){
        transmittable_threadlocal.set(2);
        // 需要用TTL的pool
        ExecutorService ttlpool = TtlExecutors.getTtlExecutorService(
                new ThreadPoolExecutor(1,1,10, TimeUnit.DAYS,new ArrayBlockingQueue<>(10))
        );
        ttlpool.execute(()->{
            Integer res = transmittable_threadlocal.get();
            System.out.println(res);
        });

        // todo 此时父线程更新了，线程池中的子线程可以感知
        transmittable_threadlocal.set(3);
        ttlpool.execute(()->{
            Integer res = transmittable_threadlocal.get();
            System.out.println(res); // 3
        });
        ttlpool.shutdown();
    }



    public static void main(String[] args) {
//        test1();
//        test2();
        test3();

    }
}
