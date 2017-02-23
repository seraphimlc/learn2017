package com.etime.arch.redis.rw.exception;

/**
 * Created by huitailang on 17/2/23.
 */
public class RedisOperationException extends Exception {
    public RedisOperationException() {

    }

    public RedisOperationException(String message) {
        super(message);
    }
}
