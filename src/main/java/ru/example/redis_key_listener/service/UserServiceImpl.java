package ru.example.redis_key_listener.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.example.redis_key_listener.model.User;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Profile("redis")
public class UserServiceImpl implements UserService {
    private static final String KEY_PREFIX = "user:";

    private final RedisTemplate<String, User> redisTemplate;

    public void saveUser(String id, User user) {
        redisTemplate.opsForValue().set(KEY_PREFIX + id, user, 1, TimeUnit.MINUTES);
    }

    public User getUser(String id) {
        return redisTemplate.opsForValue().get(KEY_PREFIX + id);
    }

    public void deleteUser(String id) {
        redisTemplate.delete(KEY_PREFIX + id);
    }
}
