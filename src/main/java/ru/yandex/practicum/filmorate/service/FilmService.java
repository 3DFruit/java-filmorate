package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {
    FilmStorage filmStorage;
    UserStorage userStorage;
    ValidationService validationService;

    @Autowired
    FilmService(FilmStorage filmStorage, UserStorage userStorage, ValidationService validationService) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
        this.validationService = validationService;
    }

    public Collection<Film> getFilms() {
        return filmStorage.getFilms();
    }

    public Film addFilm(Film film) {
        validationService.checkFilmReleaseDate(film.getReleaseDate());
        return filmStorage.addFilm(film);
    }

    public Film updateFilm(Film film) {
        validationService.checkFilmReleaseDate(film.getReleaseDate());
        validationService.checkFilmsExistence(film.getId());
        return filmStorage.updateFilm(film);
    }

    public Film getFilm(int id) {
        validationService.checkFilmsExistence(id);
        return filmStorage.getFilm(id);
    }

    public void addLike(int filmId, int userId) {
        validationService.checkFilmsExistence(filmId);
        validationService.checkUsersExistence(userId);
        filmStorage.getFilm(filmId).getLikes().add(userId);
    }

    public void removeLike(int filmId, int userId) {
        validationService.checkFilmsExistence(filmId);
        validationService.checkUsersExistence(userId);
        filmStorage.getFilm(filmId).getLikes().remove(userId);
    }

    public List<Film> getMostPopularFilms(int count) {
        return filmStorage.getFilms().stream()
                .sorted((o1, o2) -> -1 * Integer.compare(o1.getLikes().size(), o2.getLikes().size()))
                .limit(count)
                .collect(Collectors.toList());

    }
}
