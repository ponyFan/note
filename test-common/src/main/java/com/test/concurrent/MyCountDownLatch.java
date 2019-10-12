package com.test.concurrent;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * 自定义countDownLatch
 * @author zelei.fan
 * @date 2019/10/10 23:12
 */
public class MyCountDownLatch {

    private final MyCountDownLatch.Sync sync;

    /**
     * 初始化时设置state的值（state再AQS中用于锁的计数，当state为0时则表示锁处于释放状态，可以获取；大于0的话则被占用）
     * @param count
     */
    public MyCountDownLatch(int count) {
        if (count < 0) throw new IllegalArgumentException("count < 0");
        this.sync = new Sync(count);
    }

    /**
     * await方法实际上是获取锁的过程，即等待state为0，
     * @throws InterruptedException
     */
    public void await() throws InterruptedException {
        System.out.println("await " + Thread.currentThread().getName());
        sync.acquireSharedInterruptibly(1);
    }

    /**
     * countDown则是释放锁的过程，因为一开始初始化的时候state不为0，需要释放（即每次把state减1）
     */
    public void countDown() {
        sync.releaseShared(1);
    }

    private static final class Sync extends AbstractQueuedSynchronizer {
        private static final long serialVersionUID = 4982264981922014374L;

        Sync(int count) {
            setState(count);
        }

        int getCount() {
            return getState();
        }

        protected int tryAcquireShared(int acquires) {
            System.out.println("tryAcquireShared " + Thread.currentThread().getName() +" "+ ((getState() == 0) ? 1 : -1));
            return (getState() == 0) ? 1 : -1;
        }

        protected boolean tryReleaseShared(int releases) {
            // Decrement count; signal when transition to zero
            for (;;) {
                int c = getState();
                if (c == 0)
                    return false;
                int nextc = c-1;
                System.out.println("tryReleaseShared " + Thread.currentThread().getName() +" "+ c +" "+ nextc);
                if (compareAndSetState(c, nextc))
                    return true;
            }
        }
    }
}
