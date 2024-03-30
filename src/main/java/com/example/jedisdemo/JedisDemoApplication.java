package com.example.jedisdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication
public class JedisDemoApplication {

    @Autowired
    private RedisOperations redisOperations;

    public static void main(String[] args) {
        SpringApplication.run(JedisDemoApplication.class, args);
    }


    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            System.out.println();

            // truncate the database
            redisOperations.flushAll();

            //Put simple string key-value pair
            redisOperations.putStringToRedis("String key", "String value");
            //and print it
            System.out.println(redisOperations.getStringFromRedis("String key"));

            //Put whole list of Strings
            ArrayList<String> list = new ArrayList<>();
            for (int i = 0; i < 54; i++) {
                list.add("value: " + i);
            }
            redisOperations.putListToRedis("Somelist", list);

            //Get the list from Redis and print first 10 elements
            redisOperations.getListFromRedis("Somelist").stream().limit(10).forEach(System.out::println);

            //Put error stream
            redisOperations.putStreamToRedis("streamKey", "streamValue");
            try{
                throw new IllegalArgumentException("Everything is ok");
            } catch (
                    IllegalArgumentException e) {
                redisOperations.putStreamToRedis(e.getMessage(), e.toString());
            }
        };
    }
}