package com.edu.service.impl;

import com.edu.service.RedisService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;


@Service("redisServer")
public class RedisServiceImpl implements RedisService {
    @Override
    public <T> void put(String key, T obj) {

    }

    @Override
    public <T> void put(String key, T obj, int timeout) {

    }

    @Override
    public <T> void put(String key, T obj, int timeout, TimeUnit unit) {

    }

    @Override
    public <T> T get(String key, Class<T> cls) {
        return null;
    }

    @Override
    public <E, T extends Collection<E>> T get(String key, Class<E> cls, Class<T> collectionCls) {
        return null;
    }

    @Override
    public <T> T putIfAbsent(String key, Class<T> cls, Supplier<T> supplier) {
        return null;
    }

    @Override
    public <T> T putIfAbsent(String key, Class<T> cls, Supplier<T> supplier, int timeout) {
        return null;
    }

    @Override
    public <E, T extends Collection<E>> T putIfAbsent(String key, Class<E> cls, Class<T> collectionCls, Supplier<T> supplier) {
        return null;
    }

    @Override
    public boolean exists(String key) {
        return false;
    }

    @Override
    public void del(String key) {

    }

    @Override
    public boolean expire(String key, long timeout, TimeUnit unit) {
        return false;
    }

    @Override
    public boolean expire(String key, long timeout) {
        return false;
    }

    @Override
    public void put(String key, String value) {

    }

    @Override
    public void put(String key, String value, int timeout) {

    }

    @Override
    public void put(String key, String value, int timeout, TimeUnit unit) {

    }

    @Override
    public String get(String key) {
        return null;
    }

    @Override
    public void putHash(String key, Map<Object, Object> m) {

    }

    @Override
    public Map<Object, Object> getHash(String key) {
        return null;
    }
}
