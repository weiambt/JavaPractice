package _02JUC.aqs;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 *  非公平 + 不可重入锁
 */

public class NoneFairSync {
    private Sync sync = new Sync();

    void lock(){
        sync.acquire(1); // AQS底层是先调用acquire，该方法再调用tryAcquire
    }

    void unlock(){
        sync.release(1); //同理
    }

    // 需要一个内部类继承AQS并实现加解锁方法
    static class Sync extends AbstractQueuedSynchronizer {
        @Override
        protected boolean tryAcquire(int arg) {
            // 尝试CAS获取锁，如果state=0(表示空闲)就占有
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            if (getExclusiveOwnerThread() != Thread.currentThread()) {
                return false;
            }
            // 设置state为0，取消占用线程标记
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }


//        @Override
//        protected boolean isHeldExclusively() {
//            return getState() == 1;
//        }
    }


    public static void main(String[] args) {

        // 测试1：不可重入
//        NoneFairSync lock = new NoneFairSync();
//        for (int i = 0; i < 10; i++) {
//            new Thread(()-> {
//                lock.lock();
//                lock.unlock();
//            }).start();
//        }

        // 测试2：可重入
        NoneFairSync2 lock2 = new NoneFairSync2();
        lock2.lock();
        lock2.lock();
        lock2.lock();
        lock2.unlock();
        lock2.lock();
        lock2.unlock();
        lock2.unlock();
        lock2.unlock();

    }

}

/**
 *  非公平+ 可重入
 */
class NoneFairSync2{
    private Sync sync = new Sync();
    void lock(){
        sync.acquire(1);
    }
    void unlock(){
        sync.release(1);
    }

    // 需要一个内部类继承AQS并实现加解锁方法
    static class Sync extends AbstractQueuedSynchronizer {
        @Override
        protected boolean tryAcquire(int arg) {
            // 尝试CAS获取锁，如果state=0(表示空闲)就占有
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            // 可重入判断
            else if(getExclusiveOwnerThread() == Thread.currentThread()){
                setState(getState()+1);
                System.out.println("thread 重入"+ getState());
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            if (getExclusiveOwnerThread() != Thread.currentThread()) {
                return false;
            }
            int cnt = getState();
            if (cnt == 0) {
                return true;
            }
            setState(cnt-1);
            // 最后一个线程就情况
            if (cnt == 1) {
                setExclusiveOwnerThread(null);
            }

            return true;
        }


//        @Override
//        protected boolean isHeldExclusively() {
//            return getState() == 1;
//        }
    }


}
