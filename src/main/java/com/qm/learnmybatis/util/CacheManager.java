package com.qm.learnmybatis.util;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Component
public class CacheManager {
    
    private final ConcurrentHashMap<String, CacheEntry> cache = new ConcurrentHashMap<>();
    
    public <T> void put(String key, T value) {
        put(key, value, 10, TimeUnit.MINUTES); // 默认10分钟过期
    }
    
    public <T> void put(String key, T value, long duration, TimeUnit timeUnit) {
        long expireTime = System.currentTimeMillis() + timeUnit.toMillis(duration);
        cache.put(key, new CacheEntry(value, expireTime));
    }
    
    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        CacheEntry entry = cache.get(key);
        if (entry == null) {
            return null;
        }
        
        if (System.currentTimeMillis() > entry.expireTime) {
            cache.remove(key);
            return null;
        }
        
        return (T) entry.value;
    }
    
    public void remove(String key) {
        cache.remove(key);
    }
    
    public void clear() {
        cache.clear();
    }
    
    public int size() {
        return cache.size();
    }
    
    private static class CacheEntry {
        private final Object value;
        private final long expireTime;
        
        public CacheEntry(Object value, long expireTime) {
            this.value = value;
            this.expireTime = expireTime;
        }
    }
}