package com.zwj.springboot.demo.cache;

import io.lettuce.core.RedisException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RedisService {

    private RedisTemplate redisTemplate;

    private Environment env;

    public RedisService(Environment env) {
        this.env = env;
    }

    /**
     *
     * @Description: 序列化操作。
     *
     */
    @Resource
    public void setRedisTemplate(RedisTemplate redisTemplate) throws ClassNotFoundException {
        String keySerializer = env.getProperty("spring.redis.key-serializer");
        String valueSerializer = env.getProperty("spring.redis.value-serializer");
        if (StringUtils.hasLength(keySerializer)) {
            redisTemplate.setKeySerializer(newInstance((Class<RedisSerializer>) Class.forName(keySerializer)));
        }
        if (StringUtils.hasLength(keySerializer)) {
            redisTemplate.setValueSerializer(newInstance((Class<RedisSerializer>) Class.forName(valueSerializer)));
        }
        this.redisTemplate = redisTemplate;
    }

    /**
     * Instantiate the class
     */
    public static <T> T newInstance(Class<T> c) {
        try {
            return c.newInstance();
        } catch (IllegalAccessException e) {
            throw new RedisException("Could not instantiate class " + c.getName(), e);
        } catch (InstantiationException e) {
            throw new RedisException("Could not instantiate class " + c.getName() + " Does it have a public no-argument constructor?", e);
        } catch (NullPointerException e) {
            throw new RedisException("Requested class was null", e);
        }
    }

    /**
     * 写入缓存
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public boolean increment(final String key, Long value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.increment(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * 写入缓存设置时效时间
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 批量删除对应的value
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 删除对应的value
     * @param key
     */
    public boolean remove(final String key) {
        return redisTemplate.delete(key);
    }
    /**
     * 判断缓存中是否有对应的value
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 设置缓存失效时间 秒
     * @param expiredSeconds
     * @return
     */
    public boolean expire(String key, final Long expiredSeconds) {
        return redisTemplate.expire(key, expiredSeconds, TimeUnit.SECONDS);
    }

    /**
     * 读取缓存
     * @param key
     * @return
     */
    public Object get(final String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }
    /**
     * 哈希 添加
     * @param key
     * @param hashKey
     * @param value
     */
    public void hmSet(String key, Object hashKey, Object value){
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        hash.put(key,hashKey,value);
    }
    
    public List<Object> mulitiGet(String... keys) {
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        return  operations.multiGet(Arrays.asList(keys));
    }

    /**
     * 哈希 添加 多个值
     * @param key
     * @param m
     */
    public void hmSetAll(String key, Map<Object, Object> m){
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        hash.putAll(key, m);
    }

    /**
     * 哈希 删除
     * @param key
     * @param hashKey
     */
    public Long hmDel(String key, Object... hashKey){
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.delete(key, hashKey);
    }

    /**
     * 哈希获取数据
     * @param key
     * @param hashKey
     * @return
     */
    public Object hmGet(String key, Object hashKey){
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.get(key,hashKey);
    }

    /**
     * 哈希长度
     * @param key
     * @return
     */
    public Long hmLen(String key){
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.size(key);
    }


    /**
     * 哈希批量获取数据
     * @param key
     * @param options
     * @return
     */
    public Cursor<Map.Entry<Object, Object>> hmScan(String key, ScanOptions options){
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.scan(key, options);
    }

    /**
     * 列表添加
     * @param k
     * @param v
     */
    public void lPush(String k,Object v){
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.rightPush(k,v);
    }


    /**
     * 列表长度
     * @param k
     */
    public Long lLen(String k){
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.size(k);
    }


    /**
     * 列表批量添加
     * @param k
     * @param v
     */
    public void lPushAll(String k, Object... v){
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.rightPushAll(k,v);
    }


    /**
     * 列表获取
     * @param k
     * @param l
     * @param l1
     * @return
     */
    public List<Object> lRange(String k, long l, long l1){
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.range(k,l,l1);
    }

    /**
     * 集合添加
     * @param key
     * @param value
     */
    public void add(String key,Object value){
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        set.add(key,value);
    }

    /**
     * 集合获取
     * @param key
     * @return
     */
    public Set<Object> setMembers(String key){
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        return set.members(key);
    }

    /**
     * 判断集合中是否存在成员
     * @param key
     * @param  member
     * @return
     */
    public Boolean existsMember(String key, Object member){
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        return set.isMember(key, member);
    }

    /**
     * 有序集合添加
     * @param key
     * @param value
     * @param scoure
     */
    public void zAdd(String key,Object value,double scoure){
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        zset.add(key,value,scoure);
    }

    /**
     * 有序集合获取
     * @param key
     * @param scoure
     * @param scoure1
     * @return
     */
    public Set<Object> rangeByScore(String key,double scoure,double scoure1){
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        return zset.rangeByScore(key, scoure, scoure1);
    }

}
