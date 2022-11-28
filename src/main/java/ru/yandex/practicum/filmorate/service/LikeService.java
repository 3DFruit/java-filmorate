package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.storage.like.LikeStorage;

@Service
public class LikeService {
    LikeStorage likeStorage;

    @Autowired
    LikeService(@Qualifier("InMemoryLikeStorage") LikeStorage likeStorage){
        this.likeStorage = likeStorage;
    }

    public void addLike(int filmId, int userId) {
        likeStorage.addLike(filmId, userId);
    }

    public void removeLike(int filmId, int userId) {
        likeStorage.removeLike(filmId, userId);
    }
}
