package ru.example.redis_key_listener.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.RedisTemplate;
import ru.example.redis_key_listener.model.User;
import ru.example.redis_key_listener.service.UserService;
import ru.example.redis_key_listener.service.UserServiceImpl;

@Configuration
@Profile("redis")
public class RedisUserServiceConfig {

    @Bean
    public UserService userService(RedisTemplate<String, User> redisTemplate) {
        return new UserServiceImpl(redisTemplate);
    }
}
