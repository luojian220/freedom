package top.softone.freedom.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

/**
 * @author luojian
 * @version 1.0
 * @date: 2021年03月07日 14:19
 * @since JDK 1.8
 */
@Configuration
@Component
public class RedisConfig {


    @Primary
    @Bean
    public RedisOperations redisOperations(RedisTemplate<Object, Object> redisOperations) {
        redisOperations.setKeySerializer(StringRedisSerializer.UTF_8);
        //redisOperations.setValueSerializer(new Jackson2JsonRedisSerializer(Object.class));
//        redisOperations.setHashKeySerializer(StringRedisSerializer.UTF_8);
        return redisOperations;
    }
}
