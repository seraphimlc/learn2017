package com.etime.arch.redis.lock;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by huitailang on 17/2/21.
 * 测试分布式锁
 */
public class DistributedLockTest {
    private DistributedLock distributedLock;
    private Logger LOGGER = LoggerFactory.getLogger(DistributedLockTest.class);

    @Before
    public void init() {
        distributedLock = new DistributedLock();
    }
}
