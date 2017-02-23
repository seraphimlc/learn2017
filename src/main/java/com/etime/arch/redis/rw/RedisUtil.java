package com.etime.arch.redis.rw;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by huitailang on 17/2/22.
 * Redis操作工具类，支持读写分离
 */
public class RedisUtil {
    private static RedisConnectionHolder connectionHolder = new RedisConnectionHolder();
    private static JedisPool readJedisPool;
    private static JedisPool writeJedisPool;

    static {
        readJedisPool = new JedisPool(connectionHolder.getReadRedisHost(), connectionHolder.getReadRedisPort());
        writeJedisPool = new JedisPool(connectionHolder.getWriteRedisHost(), connectionHolder.getWriteRedisPort());
    }

    public static String set(final String key, String value) {
        Jedis jedis = writeJedisPool.getResource();
        return jedis.set(key, value);
    }

    public static String get(final String key) {
        Jedis jedis = readJedisPool.getResource();
        return jedis.get(key);
    }
}
