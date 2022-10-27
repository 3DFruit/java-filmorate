package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();
    private int nextId = 1;

    @GetMapping
    public Collection<User> getUsers() {
        return users.values();
    }

    @PostMapping
    public User addUser(@Valid @RequestBody User user) throws ValidationException {
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.debug("Дата рождения должна быть не позднее {}",
                    LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            throw new ValidationException("Данные о пользователе не прошли валидацию");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        user.setId(nextId);
        users.put(nextId, user);
        log.debug("Добавлен пользователь с id {}", user.getId());
        nextId++;
        return user;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) throws ValidationException {
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.debug("Дата рождения должна быть не позднее {}",
                    LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            throw new ValidationException("Данные о пользователе не прошли валидацию");
        }
        int id = user.getId();
        if (!users.containsKey(id)) {
            log.debug("Пользователь с id {} не найден", id);
            throw new ValidationException("Не удалось обновить данные о пользователе");
        }
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        log.debug("Обновлен пользователь с id {}", id);
        users.put(id, user);
        return user;
    }
}
