package com.example.jedisdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StreamOperations;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class RedisOperations {

    private final StreamOperations<String, String, String> streamOps;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private RedisTemplate<String, byte[]> redisTemplateBytes;

    public RedisOperations(RedisTemplate<String, String> redisTemplate) {
        this.streamOps = redisTemplate.opsForStream();
    }

    public void putFileToRedis(String fileName) throws IOException {
        String filePath = "src/main/resources/";
        byte[] bytes = Files.readAllBytes(Paths.get(filePath + fileName));
        redisTemplateBytes.opsForValue().set(fileName, bytes);
    }

    public void putStringToRedis(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public String getStringFromRedis(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void putListToRedis(String key, List<String> list) {
        redisTemplate.opsForList().rightPushAll(key, list);
    }

    public List<String> getListFromRedis(String key) {
        List<String> list = redisTemplate.opsForList().range(key, 0, -1);
        return list;

    }

    public void flushAll() {
        redisTemplate.getConnectionFactory().getConnection().flushAll();
    }

    public void putStreamToRedis(String key, String value) {
        ObjectRecord<String, String> record = StreamRecords.newRecord()
                .ofObject(value)
                .withStreamKey(key);
        streamOps.add(record);
    }
}
