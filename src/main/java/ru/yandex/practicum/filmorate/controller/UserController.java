package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();

    @GetMapping
    public Collection<User> getUsers() {
        return users.values();
    }

    @PostMapping
    public User addUser(@RequestBody User user) throws ValidationException {
        if (!validate(user)) {
            log.debug("Не пройдена валиданция данных о пользователе");
            throw new ValidationException("Ошибка добавления пользователя, данные не прошли валидацию");
        }
        log.debug("Добавлен пользователь с id {}", user.getId());
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User user) throws ValidationException {
        if (!validate(user)) {
            log.debug("Не пройдена валиданция данных о пользователе");
            throw new ValidationException("Ошибка обновления пользователя, данные не прошли валидацию");
        }
        log.debug("Добавлен пользователь с id {}", user.getId());
        users.put(user.getId(), user);
        return user;
    }

    private boolean validate(User user) {
        return user.getEmail().contains("@")
                && !user.getEmail().isBlank()
                && !user.getLogin().isBlank()
                && !user.getLogin().contains(" ")
                && user.getBirthday().isBefore(LocalDate.now());
    }
}
