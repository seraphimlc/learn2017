package com.etime.arch.redis.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by huitailang on 17/2/21.
 * 模拟使用锁的场景
 */
class Task implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(Task.class);
    private DistributedLock distributedLock;

    public Task(DistributedLock distributedLock) {
        this.distributedLock = distributedLock;
    }

    @Override
    public void run() {
        String key = String.valueOf("stock");
        String value = null;
        long tryWaitTime = 3000;
        int lockTimeout = 2;

        try {
            value = distributedLock.tryLock(key, tryWaitTime, lockTimeout);
            if (value != null) {
                LOGGER.info(Thread.currentThread().getName() + "获得锁");

                LOGGER.info(Thread.currentThread().getName() + "执行业务操作");

                LOGGER.info(value);
            }
        } finally {
            if (value != null) {
                if (distributedLock.unlock(key, value)) {
                    LOGGER.info(Thread.currentThread().getName() + "释放锁");
                }
            }
        }
    }

    public static void main(String[] args) {
        DistributedLock distributedLock = new DistributedLock();
        Thread[] threads = new Thread[10];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new Task(distributedLock), "线程" + (i + 1));
        }

        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
    }
}
