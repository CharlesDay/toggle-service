package com.charlie.toggleservice.config;

import com.charlie.toggleservice.repositories.ToggleRepository;
import com.charlie.toggleservice.services.ToggleService;
import com.charlie.toggleservice.services.ToggleServiceRedis;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
@EnableRedisRepositories
@ConditionalOnProperty(value = "toggle.service", havingValue = "redis")
@Import(RedisAutoConfiguration.class)
public class RedisConfig {

    @Bean
    public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<?, ?> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setValueSerializer(new JdkSerializationRedisSerializer());
        template.setKeySerializer(new StringRedisSerializer());
        template.setKeySerializer(new JdkSerializationRedisSerializer());
        return template;
    }

    @Bean
    public ToggleService toggleService(ToggleRepository toggleRepository) {
        return new ToggleServiceRedis(toggleRepository);
    }
}