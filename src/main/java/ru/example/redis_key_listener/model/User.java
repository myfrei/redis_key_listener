package ru.example.redis_key_listener.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class User {
    @NotNull
    private String name;
    @NotNull
    private String surname;
    @NotNull
    private Integer age;
}
