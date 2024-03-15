package test;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class TestRedisComponent {

    private final RedisTemplate<String, Object> redisTemplate;

    public TestRedisComponent(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void testRedisConnection() {
        String key = "test-key";
        String value = "test-value";

        // Store a value in Redis
        redisTemplate.opsForValue().set(key, value);
        System.out.println("Stored value in Redis: " + value);

        // Retrieve the value from Redis
        Object retrievedValue = redisTemplate.opsForValue().get(key);
        System.out.println("Retrieved value from Redis: " + retrievedValue);
    }
}
