package com.hixtrip.sample.infra.db.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 注： 同一个键存再次进行存储就是修改操作
 *
 * @Author：duay
 * @Date：2024/4/13 10:12
 */
@Component
public class RedisTemplateUtil implements Serializable {

    @Autowired
    private RedisTemplate redisTemplate;

    private static RedisTemplate redisTem;

    @PostConstruct
    public void initRedisTem() {
        redisTem = redisTemplate;
    }

    /**
     * 设置 String 类型 key-value
     *
     * @param key
     * @param value
     */
    public static void set(String key, Object value) {
        redisTem.opsForValue().set(key, value);
    }

    /**
     * 获取 String 类型 key-value
     *
     * @param key
     * @return
     */
    public static <T> T get(String key) {
        Object value = redisTem.opsForValue().get(key);
        if (Objects.isNull(key)) {
            return null;
        }
        return (T) value;
    }


    /**
     * 判断redis中是否含有该键
     *
     * @param key 键值
     * @return Boolean值 false 没有此键， true 含有此键
     */
    public static boolean hasKey(String key) {
        //返回boolean值
        return redisTem.hasKey(key);
    }

    /**
     * 删除键值
     *
     * @param key 键
     * @return 返回删除结果
     */
    public static boolean delete(String key) {
        Boolean delete = redisTem.delete(key);
        return delete;
    }

    /**
     * 通过集合中的所有key删除对应的所有值
     *
     * @param keys 集合keys
     * @return 返回boolean值
     */
    public static boolean delete(Collections keys) {
        Boolean delete = redisTem.delete(keys);
        return delete;
    }

    /**
     * 获取键的过期时间
     *
     * @param key 键
     * @return 返回long类型的时间数值
     */
    public static long getExpire(String key) {
        return redisTem.getExpire(key);
    }

    /**
     * 过期时间设置
     *
     * @param key           键
     * @param expireMinutes 过期时间
     * @return 返回设置成功
     */
    public static boolean setExpire(String key, long expireMinutes) {
        Boolean expire = redisTem.expire(key, Duration.ofMinutes(expireMinutes));
        return expire;
    }

    public static boolean setExpireByMillis(String key, long expireMillis) {
        Boolean expire = redisTem.expire(key, Duration.ofMillis(expireMillis));
        return expire;
    }

    public static boolean setExpireBySecond(String key, long expireSeconds) {
        Boolean expire = redisTem.expire(key, Duration.ofSeconds(expireSeconds));
        return expire;
    }

    public static boolean setExpireByHour(String key, long expireHours) {
        Boolean expire = redisTem.expire(key, Duration.ofHours(expireHours));
        return expire;
    }

    public static boolean setExpireByDay(String key, long expireDays) {
        Boolean expire = redisTem.expire(key, Duration.ofMinutes(expireDays));
        return expire;
    }


}


