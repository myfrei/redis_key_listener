package ru.example.redis_key_listener.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import ru.example.redis_key_listener.service.InMemoryUserService;
import ru.example.redis_key_listener.service.UserService;

@Configuration
@Profile("!redis")
public class InMemoryConfig {

    @Bean
    public UserService userService() {
        return new InMemoryUserService();
    }
}
