package ru.yandex.practicum.filmorate.storage.like;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component("InMemoryLikeStorage")
public class InMemoryLikeStorage implements LikeStorage {
    Map<Integer, Set<Integer>> filmLikes = new HashMap<>();
    UserStorage userStorage;
    FilmStorage filmStorage;

    @Autowired
    InMemoryLikeStorage(@Qualifier("InMemoryUserStorage") UserStorage userStorage,
                        @Qualifier("InMemoryFilmStorage") FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    @Override
    public void addLike(int filmId, int userId) {
        isFilmExist(filmId);
        isUserExist(userId);
        if (!filmLikes.containsKey(filmId)) {
            filmLikes.put(filmId, new HashSet<>());
        }
        filmLikes.get(filmId).add(userId);
    }

    @Override
    public void removeLike(int filmId, int userId) {
        isFilmExist(filmId);
        isUserExist(userId);
        if (filmLikes.containsKey(filmId)) {
            filmLikes.get(filmId).remove(userId);
        }
    }

    @Override
    public Integer getLikesQuantity(int filmId) {
        isFilmExist(filmId);
        if (!filmLikes.containsKey(filmId)) {
            return 0;
        }
        return filmLikes.get(filmId).size();
    }

    private void isUserExist(int... args) {
        for (int arg : args) {
            if (userStorage.getUser(arg) == null) {
                throw new ObjectNotFoundException("Не найден пользователь с id " + arg);
            }
        }
    }

    private void isFilmExist(int... args) {
        for (int arg : args) {
            if (filmStorage.getFilm(arg) == null) {
                throw new ObjectNotFoundException("Не найден фильм с id " + arg);
            }
        }
    }
}
