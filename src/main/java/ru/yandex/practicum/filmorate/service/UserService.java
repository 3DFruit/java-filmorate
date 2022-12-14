package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.InvalidParameterException;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;

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
        user = userStorage.updateUser(user);
        if (user == null) {
            throw  new ObjectNotFoundException("Пользователь не найден");
        }
        return userStorage.updateUser(user);
    }

    public User getUser(int id) {
        if (id < 1) {
            throw new InvalidParameterException("Неверные параметры запроса");
        }
        User user = userStorage.getUser(id);
        if (user == null) {
            throw new ObjectNotFoundException("Не найден пользователь с id - " + id);
        }
        return user;
    }
}
