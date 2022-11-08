package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {

    private int nextId = 1;
    private final Map<Integer, User> users = new HashMap<>();

    @Override
    public User addUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        user.setId(nextId);
        users.put(nextId, user);
        nextId++;
        return user;
    }

    @Override
    public void removeUserById(int id) {
        users.remove(id);
    }

    @Override
    public User updateUser(User user) {
        int id = user.getId();
        if (!users.containsKey(id)) {
            throw new UserNotFoundException("Не найден пользователь с id - " + id);
        }
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        users.put(id, user);
        return user;
    }

    @Override
    public Collection<User> getUsers() {
        return users.values();
    }

    @Override
    public User getUser(int id) {
        return users.get(id);
    }
}
