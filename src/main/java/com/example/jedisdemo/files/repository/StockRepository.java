package com.example.jedisdemo.files.repository;

import com.example.jedisdemo.files.model.StockPrice;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Repository
public class StockRepository {
    private static final String KEY_PREFIX = "stock:";
    private final RedisTemplate<String, Object> redisTemplate;

    private final HashOperations<String, String, String> hashOperations;

    public StockRepository(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }

    public void saveStock(StockPrice stockPrice) {
        String key = KEY_PREFIX + stockPrice.getSymbol();

        Map<String, String> stockMap = new HashMap<>();
        stockMap.put("symbol", stockPrice.getSymbol());
        stockMap.put("price", stockPrice.getPrice().toString());
        stockMap.put("change", stockPrice.getChange().toString());
        stockMap.put("percentChange", stockPrice.getPercentChange().toString());
        hashOperations.putAll(key, stockMap);
    }

    public StockPrice findStock(String symbol) {
        String key = KEY_PREFIX + symbol;
        Map<String, String> stockMap = hashOperations.entries(key);

        if (stockMap.isEmpty()) {
            return null;
        }

        String symbolValue = stockMap.get("symbol");
        BigDecimal price = new BigDecimal(stockMap.get("price"));
        BigDecimal change = new BigDecimal(stockMap.get("change"));
        BigDecimal percentChange = new BigDecimal(stockMap.get("percentChange"));

        return new StockPrice(symbolValue, price, change, percentChange);
    }

}