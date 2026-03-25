package com.qm.learnmybatis.controller;

import com.qm.learnmybatis.util.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/cache")
public class CacheTestController {
    
    @Autowired
    private CacheManager cacheManager;
    
    @GetMapping("/put")
    public String putCache(@RequestParam String key, @RequestParam String value) {
        cacheManager.put(key, value);
        return "缓存已添加: " + key + " = " + value;
    }
    
    @GetMapping("/get")
    public String getCache(@RequestParam String key) {
        String value = cacheManager.get(key);
        return value != null ? "缓存值: " + value : "缓存中未找到该键";
    }
    
    @GetMapping("/remove")
    public String removeCache(@RequestParam String key) {
        cacheManager.remove(key);
        return "缓存已删除: " + key;
    }
    
    @GetMapping("/clear")
    public String clearCache() {
        cacheManager.clear();
        return "缓存已清空";
    }
    
    @GetMapping("/size")
    public String cacheSize() {
        return "当前缓存大小: " + cacheManager.size();
    }
    
    @GetMapping("/put-expire")
    public String putCacheWithExpire(@RequestParam String key, @RequestParam String value, 
                                   @RequestParam long seconds) {
        cacheManager.put(key, value, seconds, TimeUnit.SECONDS);
        return "缓存已添加: " + key + " = " + value + " (有效期: " + seconds + "秒)";
    }
}