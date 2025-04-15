package _02JUC.threadlocal;

/*
    inheritableThreadLocal才能进行子线程的传递

    底层通过Thread中的inheritableThrealocal变量维护
 */

public class InheritableThreadLocalTest {
    static ThreadLocal<Integer> threadlocal = new ThreadLocal<>();
    static InheritableThreadLocal<Integer> inheritable_threadlocal = new InheritableThreadLocal<>();

    public static void main(String[] args) {
        threadlocal.set(-1);
        inheritable_threadlocal.set(-2);

        // 开启一个子线程,
        new Thread(() -> {
            Integer res = threadlocal.get();
            System.out.println(res); // null

            res = inheritable_threadlocal.get();
            System.out.println(res);// -1
        }).start();
    }
}
