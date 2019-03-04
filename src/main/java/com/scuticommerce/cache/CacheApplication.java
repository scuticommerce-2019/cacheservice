package com.scuticommerce.cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@SpringBootApplication
public class CacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(CacheApplication.class, args);
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplate() {
        RedisTemplate<?, ?> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new GenericToStringSerializer<Object>(Object.class));
        template.setHashValueSerializer(new JdkSerializationRedisSerializer());
        template.setValueSerializer(new JdkSerializationRedisSerializer());
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

}
