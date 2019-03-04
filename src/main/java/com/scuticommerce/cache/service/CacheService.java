package com.scuticommerce.cache.service;

import com.scuticommerce.cache.model.CacheData;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CacheService {

    public static final int SIZE = 100;

    @Autowired
    RedisTemplate redisTemplate;

    public <T> String storeCache(T key, T value){

        CacheData data = new CacheData<>();
        data.setKey(key.toString());
        data.setValue(value);
        ArrayList<CacheData> list = new ArrayList<>();
        list.add(data);
        writeWithPipeline(list);

        return key.toString()+":"+value.toString();
    }

    public <T> T getCache(String key) {

        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        T data = (T) values.get(key);

        return data;
    }

    public void writeWithPipeline(List<CacheData> data){

        long start = System.currentTimeMillis();

        List<List<CacheData>> batches = ListUtils.partition(new ArrayList<>(data), SIZE);

        for (List<CacheData> batch : batches) {

            redisTemplate.executePipelined((RedisCallback<Object>) conn -> {

                for (CacheData cacheData : batch) {

                    String redisKey = cacheData.getKey();
                    conn.set(redisTemplate.getKeySerializer().serialize(redisKey),
                            redisTemplate.getValueSerializer().serialize(cacheData.getValue()));
                }
                return null;
            });
        }

        long end = System.currentTimeMillis();

        System.out.println( "Time taken "+ (end -start) + "Ms");

    }
}
