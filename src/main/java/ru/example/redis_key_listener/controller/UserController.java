package ru.example.redis_key_listener.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.redis_key_listener.model.User;
import ru.example.redis_key_listener.service.UserService;
import ru.example.redis_key_listener.service.UserServiceImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {


    private final UserService userService;

    @PostMapping("/{id}")
    public ResponseEntity<Void> createUser(@PathVariable String id, @RequestBody User user) {
        userService.saveUser(id, user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Cacheable(cacheNames = "User", key = "#id")
    public ResponseEntity<User> getUser(@PathVariable String id) {
        User user = userService.getUser(id);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
