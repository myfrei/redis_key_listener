package ru.example.redis_key_listener.service;

import ru.example.redis_key_listener.model.User;

public interface UserService {

    void saveUser(String id, User user);

    User getUser(String id);

    void deleteUser(String id);
}
