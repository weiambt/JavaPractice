package _02JUC.aqs;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * 公平锁 + 可重入
 */

public class FairSync {
    Sync sync = new Sync();

    void lock(){
        sync.acquire(1);
    }

    void unlock(){
        sync.release(1);
    }

    static class Sync extends AbstractQueuedSynchronizer {

        @Override
        protected boolean tryAcquire(int arg) {
            int state = getState();
            if (state == 0) {
                // 如果等待队列中无元素，就尝试获取锁
                if (!hasQueuedThreads() && compareAndSetState(0, 1)) {
                    setExclusiveOwnerThread(Thread.currentThread());
                    return true;
                }
            }
            // 如果当前线程可重入
            else if (getExclusiveOwnerThread() == Thread.currentThread()) {
                setState(getState() + 1);
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            if (getExclusiveOwnerThread() != Thread.currentThread()) {
                return false;
            }
            setState(getState() - 1);
            if (getState() == 0) {
                setExclusiveOwnerThread(null);
            }
            return true;
        }

    }
}
