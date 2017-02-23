package com.etime.arch.redis.rw;

import com.etime.arch.redis.rw.config.Constants;
import com.etime.arch.redis.rw.exception.RedisOperationException;
import com.etime.arch.redis.util.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * Created by huitailang on 17/2/22.
 * REDIS读写配置
 */
public class RedisConnectionHolder {
    private static final Properties CONFIG_PROPS = PropertiesUtil.loadProps(Constants.CONFIG_FILE);
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesUtil.class);
    private String readRedisHost;
    private Integer readRedisPort;

    private String writeRedisHost;
    private int writeRedisPort;

    public RedisConnectionHolder(){
        try{
            init();
        }catch(RedisOperationException e){
            e.printStackTrace();
        }
    }

    private void init()throws RedisOperationException{
        readRedisHost = PropertiesUtil.getString(CONFIG_PROPS, Constants.READ_REDIS_HOST);
        readRedisPort = PropertiesUtil.getInt(CONFIG_PROPS, Constants.READ_REDIS_PORT);
        writeRedisHost = PropertiesUtil.getString(CONFIG_PROPS, Constants.WRITE_REDIS_HOST);
        writeRedisPort = PropertiesUtil.getInt(CONFIG_PROPS, Constants.WRITE_REDIS_PORT);
    }

    public String getReadRedisHost() {
        return readRedisHost;
    }

    public int getReadRedisPort() {
        return readRedisPort;
    }

    public String getWriteRedisHost() {
        return writeRedisHost;
    }

    public int getWriteRedisPort() {
        return writeRedisPort;
    }
}
