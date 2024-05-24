package ru.example.redis_key_listener.service;

import org.springframework.stereotype.Service;
import ru.example.redis_key_listener.model.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class InMemoryUserService implements UserService {

    private final Map<String, User> userMap = new ConcurrentHashMap<>();

    @Override
    public void saveUser(String id, User user) {
        userMap.put(id, user);
    }

    @Override
    public User getUser(String id) {
        return userMap.get(id);
    }

    @Override
    public void deleteUser(String id) {
        userMap.remove(id);
    }
}
