package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.InvalidParameterException;
import ru.yandex.practicum.filmorate.storage.like.LikeStorage;

@Service
public class LikeService {
    LikeStorage likeStorage;

    @Autowired
    LikeService(LikeStorage likeStorage){
        this.likeStorage = likeStorage;
    }

    public void addLike(int filmId, int userId) {
        if (filmId < 1 || userId < 1) {
            throw new InvalidParameterException("Неверные параметры запроса");
        }
        likeStorage.addLike(filmId, userId);
    }

    public void removeLike(int filmId, int userId) {
        if (filmId < 1 || userId < 1) {
            throw new InvalidParameterException("Неверные параметры запроса");
        }
        likeStorage.removeLike(filmId, userId);
    }
}
