package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;

@Service
public class ValidationService {
    FilmStorage filmStorage;
    UserStorage userStorage;

    @Autowired
    ValidationService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public void checkUsersExistence(int... args) {
        for (int arg : args) {
            if (userStorage.getUser(arg) == null) {
                throw new UserNotFoundException("Не найден пользователь с id " + arg);
            }
        }
    }

    public void checkFilmsExistence(int... args) {
        for (int arg : args) {
            if (filmStorage.getFilm(arg) == null) {
                throw new FilmNotFoundException("Не найден фильм с id " + arg);
            }
        }
    }

    public void checkFilmReleaseDate(LocalDate releaseDate) {
        if (releaseDate == null || releaseDate.isBefore(Film.MIN_RELEASE_DATE)) {
            throw new ValidationException("Неверная дата выпуска фильма");
        }
    }
}
