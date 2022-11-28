package ru.yandex.practicum.filmorate.storage.friendship;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface FriendshipStorage {
    Collection<User> getFriends (int userId);
    void addToFriends(int userId, int friendId);
    void removeFromFriends(int userId, int friendId);
}
