package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Collection<User> getUsers() {
        log.info("Запрошены все пользователи");
        return userService.getUsers();
    }

    @PostMapping
    public User addUser(@Valid @RequestBody User user){
        user = userService.addUser(user);
        log.info("Добавлен пользователь с id {}", user.getId());
        return user;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user){
        user = userService.updateUser(user);
        log.info("Обновлен пользователь с id {}", user.getId());
        return user;
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable int id) {
        log.info("Запрошен пользователь с id {}", id);
        return userService.getUser(id);
    }
}
