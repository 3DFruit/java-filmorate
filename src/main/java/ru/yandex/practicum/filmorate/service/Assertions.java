package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

@Service
public class Assertions {
    FilmStorage filmStorage;
    UserStorage userStorage;

    @Autowired
    Assertions(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public void assertUser(int... args) {
        for (int arg : args) {
            if (userStorage.getUser(arg) == null) {
                throw new UserNotFoundException("Не найден пользователь с id " + arg);
            }
        }
    }

    public void assertFilm(int... args) {
        for (int arg : args) {
            if (filmStorage.getFilm(arg) == null) {
                throw new FilmNotFoundException("Не найден фильм с id " + arg);
            }
        }
    }
}
