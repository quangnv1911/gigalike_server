package com.gigalike.platform.config;

import lombok.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisConfig {
    @Value("${redis.host}")
    private String redisHost;

    @Value("${redis.port}")
    private int redisPort;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        // Tạo Standalone Connection tới Redis
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration(redisHost, redisPort));
    }


    @Bean
    @Primary
    public RedisTemplate<String, Object> keyDbTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        // Tạo Jackson2JsonRedisSerializer để serialize/deserialize đối tượng Java thành JSON
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);

        // Thiết lập Jackson serializer cho key và value
        template.setKeySerializer(RedisSerializer.string());  // Key được lưu dưới dạng String
        template.setValueSerializer(serializer);              // Value sử dụng Jackson serializer

        // Thiết lập serializer cho các đối tượng trong Hash
        template.setHashKeySerializer(RedisSerializer.string());
        template.setHashValueSerializer(serializer);

        return template;
    }
}
