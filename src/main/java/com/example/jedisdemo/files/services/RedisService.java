package com.example.jedisdemo.files.services;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveSet(String key, HashSet<Object> set) {
        redisTemplate.opsForSet().add(key, set.toArray());
    }

    public Set<Object> getSet(String key) {
        return redisTemplate.opsForSet().members(key);
    }
}