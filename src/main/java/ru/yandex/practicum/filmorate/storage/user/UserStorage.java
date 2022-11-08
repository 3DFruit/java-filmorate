package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {
    User addUser(User user);
    void removeUserById(int id);
    User updateUser(User user);
    Collection<User> getUsers();

    User getUser(int id);
}
