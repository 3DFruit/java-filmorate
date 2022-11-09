package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users/{id}/friends")
public class UserFriendsController {

    UserService userService;

    @Autowired
    UserFriendsController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Collection<User> getUsers(@PathVariable int id) {
        return userService.getUser(id)
                .getFriends()
                .stream()
                .map(x -> userService.getUser(x))
                .collect(Collectors.toList());
    }

    @PutMapping("/{friendId}")
    public void addToFriends(@PathVariable("id") int userId, @PathVariable int friendId) {
        userService.addToFriends(userId, friendId);
    }

    @DeleteMapping("/{friendId}")
    public void removeToFriends(@PathVariable("id") int userId, @PathVariable int friendId) {
        userService.removeFromFriends(userId, friendId);
    }

    @GetMapping("/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable int id, @PathVariable int otherId) {
        return userService.getCommonFriends(id, otherId);
    }
}