package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    UserStorage userStorage;
    ValidationService validationService;

    @Autowired
    UserService(UserStorage userStorage, ValidationService validationService) {
        this.userStorage = userStorage;
        this.validationService = validationService;
    }

    public Collection<User> getUsers() {
        return userStorage.getUsers();
    }

    public User addUser(User user){
        return userStorage.addUser(user);
    }

    public User updateUser(User user){
        validationService.checkUsersExistence(user.getId());
        return userStorage.updateUser(user);
    }

    public User getUser(int id) {
        User user = userStorage.getUser(id);
        if (user == null) {
            throw new UserNotFoundException("Не найден пользователь с id - " + id);
        }
        return user;
    }

    public void addToFriends(int userId, int friendId) {
        validationService.checkUsersExistence(userId, friendId);
        User user = userStorage.getUser(userId);
        User friend = userStorage.getUser(friendId);
        user.getFriends().add(friendId);
        friend.getFriends().add(userId);
    }

    public void removeFromFriends(int userId, int friendId){
        validationService.checkUsersExistence(userId, friendId);
        User user = userStorage.getUser(userId);
        User friend = userStorage.getUser(friendId);
        user.getFriends().remove(friendId);
        friend.getFriends().remove(userId);
    }

    public List<User> getCommonFriends(int userId, int otherUserId) {
        validationService.checkUsersExistence(userId, otherUserId);
        User user = userStorage.getUser(userId);
        User friend = userStorage.getUser(otherUserId);
        return user.getFriends().stream()
                .filter(friend.getFriends()::contains)
                .map(x -> userStorage.getUser(x))
                .collect(Collectors.toList());
    }
}
