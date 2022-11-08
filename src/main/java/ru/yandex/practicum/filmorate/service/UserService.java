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

    @Autowired
    UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public Collection<User> getUsers() {
        return userStorage.getUsers();
    }

    public User addUser(User user){
        return userStorage.addUser(user);
    }

    public User updateUser(User user){
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
        User user = userStorage.getUser(userId);
        user.getFriends().add(friendId);
        User friend = userStorage.getUser(friendId);
        friend.getFriends().add(userId);
    }

    public void removeFromFriends(int userId, int friendId){
        User user = userStorage.getUser(userId);
        user.getFriends().remove(friendId);
        User friend = userStorage.getUser(friendId);
        friend.getFriends().remove(userId);
    }

    public List<User> getCommonFriends(int userId, int friendId) {
        User user = userStorage.getUser(userId);
        User friend = userStorage.getUser(friendId);
        return user.getFriends().stream()
                .filter(friend.getFriends()::contains)
                .map(x -> userStorage.getUser(x))
                .collect(Collectors.toList());
    }
}
