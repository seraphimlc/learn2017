package com.etime.arch.redis.lock;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.util.UUID;

/**
 * Created by huitailang on 17/2/21.
 * 基于Redis实现的分布式锁
 */
public class DistributedLock {
    private JedisPool jedisPool;

    //重试等待时间
    private final long tryWait = 300;

    public DistributedLock() {
        jedisPool = new JedisPool("192.168.1.53", 6380);
    }

    public String tryLock(String lockName, long tryWaitTimeout, int lockTimeOut) {
        String value = UUID.randomUUID().toString();
        String key = "lock:" + lockName;
        Jedis jedis = jedisPool.getResource();

        long acquireTimeEnd = System.currentTimeMillis() + tryWaitTimeout;

        while (System.currentTimeMillis() < acquireTimeEnd) {
            //获取锁并设置过期时间
            if (jedis.setnx(key, value) != 0) {
                jedis.expire(key, lockTimeOut);
                return value;
            }

            //检查过期时间，并在必要时对其更新
            else if (jedis.ttl(key) == -1) {
                jedis.expire(key, lockTimeOut);
            }

            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {

            }
        }

        return null;
    }

    public boolean unlock(String lockName, String value) {
        String key = "lock:" + lockName;
        Jedis jedis = jedisPool.getResource();

        jedis.watch(key);
        //确保当前线程还持有锁
        if (value.equals(jedis.get(key))) {
            Transaction transaction = jedis.multi();
            transaction.del(key);
            return transaction.exec().isEmpty();
        }
        jedis.unwatch();

        return false;
    }
}
