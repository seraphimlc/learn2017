package com.etime.arch.redis.rw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by huitailang on 17/2/22.
 * Redis操作工具类，支持读写分离
 * 部署一主多从
 * TODO 支持多个从节点读
 */

/**
 * add by lc
 *
 * 大体思路是对的,关于Pool的代码,再优化一下
 * 现在你的配置是写在properties里,如果我要是增加一个从,还要重启程序吗
 * 你即然已经学了zk,是不是可以把配置放到zk上,然后做一个简单的控制台,专门配置这个的
 *
 * 争取做成一个module,可以被其它项目引用
 */
public class RedisUtil {
    public static final Logger LOGGER = LoggerFactory.getLogger(RedisUtil.class);
    private static RedisConnectionHolder connectionHolder = new RedisConnectionHolder();
    private static JedisPool readJedisPool;
    private static JedisPool writeJedisPool;

    static {
        readJedisPool = new JedisPool(connectionHolder.getReadRedisHost(), connectionHolder.getReadRedisPort());
        writeJedisPool = new JedisPool(connectionHolder.getWriteRedisHost(), connectionHolder.getWriteRedisPort());
    }

    public static String set(final String key, String value) {
        Jedis jedis = null;
        String response = null;

        try{
            jedis = writeJedisPool.getResource();
            response = jedis.set(key, value);
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }

        return response;
    }

    public static String get(final String key) {
        Jedis jedis = null;
        String response = null;

        try{
            jedis = readJedisPool.getResource();
            response = jedis.get(key);
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }

        return response;
    }

    //TODO redis其它命令
}
