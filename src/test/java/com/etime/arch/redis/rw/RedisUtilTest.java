package com.etime.arch.redis.rw;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by huitailang on 17/2/23.
 * RedisUtilTest
 */
public class RedisUtilTest {
    @Test
    public void testWriteRedis(){
        String response = RedisUtil.set("redis", "redis.com");
        Assert.assertEquals("OK", response);
    }

    @Test
    public void tesReadRedis(){
        String value  = RedisUtil.get("redis");
        Assert.assertEquals("redis.com", value);
    }
}
