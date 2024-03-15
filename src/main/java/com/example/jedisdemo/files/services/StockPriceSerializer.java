package com.example.jedisdemo.files.services;

import com.example.jedisdemo.files.model.StockPrice;
import com.google.gson.Gson;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.StandardCharsets;

public class StockPriceSerializer implements RedisSerializer<StockPrice> {
    private final Gson gson = new Gson();

    @Override
    public byte[] serialize(StockPrice stockPrice) throws SerializationException {
        if (stockPrice == null) {
            return new byte[0];
        }
        return gson.toJson(stockPrice).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public StockPrice deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        String json = new String(bytes, StandardCharsets.UTF_8);
        return gson.fromJson(json, StockPrice.class);
    }
}
