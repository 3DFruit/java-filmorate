package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FriendshipService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/users/{id}/friends")
@Slf4j
public class UserFriendsController {

    FriendshipService friendshipService;

    @Autowired
    UserFriendsController(FriendshipService friendshipService) {
        this.friendshipService = friendshipService;
    }

    @GetMapping
    public Collection<User> getUsers(@PathVariable int id) {
        log.info("Запрошен список друзей пользователя с id {}", id);
        return friendshipService.getFriends(id);
    }

    @PutMapping("/{friendId}")
    public void addToFriends(@PathVariable("id") int userId, @PathVariable int friendId) {
        log.info("Пользователи с id {} и {} добавлены в друзья", userId, friendId);
        friendshipService.addToFriends(userId, friendId);
    }

    @DeleteMapping("/{friendId}")
    public void removeFromFriends(@PathVariable("id") int userId, @PathVariable int friendId) {
        log.info("Пользователи с id {} и {} убраны из друзей", userId, friendId);
        friendshipService.removeFromFriends(userId, friendId);
    }

    @GetMapping("/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable int id, @PathVariable int otherId) {
        log.info("Запрошены общие друзья пользователей с id {} и {}", id, otherId);
        return friendshipService.getCommonFriends(id, otherId);
    }
}