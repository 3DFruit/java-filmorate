package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.InvalidParameterException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.friendship.FriendshipStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FriendshipService {
    FriendshipStorage friendshipStorage;

    @Autowired
    FriendshipService(@Qualifier("InMemoryFriendshipStorage") FriendshipStorage friendshipStorage) {
        this.friendshipStorage = friendshipStorage;
    }
    public void addToFriends(int userId, int friendId) {
        if (userId == friendId) {
            throw new InvalidParameterException("Ошибка в параметрах запроса");
        }
        friendshipStorage.addToFriends(userId, friendId);
    }

    public void removeFromFriends(int userId, int friendId){
        if (userId == friendId) {
            throw new InvalidParameterException("Ошибка в параметрах запроса");
        }
        friendshipStorage.removeFromFriends(userId, friendId);
    }

    public Collection<User> getFriends(int userId){
        return friendshipStorage.getFriends(userId);
    }

    public List<User> getCommonFriends(int userId, int otherUserId) {
        Collection<User> otherUserFriends = friendshipStorage.getFriends(otherUserId);
        Collection<User> userFriends = friendshipStorage.getFriends(userId);
        if (otherUserFriends == null || userFriends == null) {
            return new ArrayList<>();
        }
        return userFriends.stream()
                .filter(otherUserFriends::contains)
                .collect(Collectors.toList());
    }
}
