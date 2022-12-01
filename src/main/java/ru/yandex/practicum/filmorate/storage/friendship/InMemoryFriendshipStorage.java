package ru.yandex.practicum.filmorate.storage.friendship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Component("InMemoryFriendshipStorage")
public class InMemoryFriendshipStorage implements FriendshipStorage {
    Map<Integer, Set<Integer>> friendships = new HashMap<>();
    UserStorage userStorage;

    @Autowired
    InMemoryFriendshipStorage(@Qualifier("InMemoryUserStorage") UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @Override
    public Collection<User> getFriends(int userId) {
        isExist(userId);
        Set<Integer> friends = friendships.get(userId);
        if (friends == null) {
            return new ArrayList<>();
        }
        return friends.stream().map(userStorage::getUser).collect(Collectors.toList());
    }

    @Override
    public void addToFriends(int userId, int friendId) {
        isExist(userId, friendId);
        if (!friendships.containsKey(userId)) {
            friendships.put(userId, new HashSet<>());
        }
        friendships.get(userId).add(friendId);
        if (!friendships.containsKey(friendId)) {
            friendships.put(friendId, new HashSet<>());
        }
        friendships.get(friendId).add(userId);
    }

    @Override
    public void removeFromFriends(int userId, int friendId) {
        isExist(userId, friendId);
        if (friendships.containsKey(userId)) {
            friendships.get(userId).remove(friendId);
        }
        if (friendships.containsKey(friendId)) {
            friendships.get(friendId).remove(userId);
        }
    }

    private void isExist(int... args) {
        for (int arg : args) {
            if (userStorage.getUser(arg) == null) {
                throw new ObjectNotFoundException("Не найден пользователь с id " + arg);
            }
        }
    }
}
