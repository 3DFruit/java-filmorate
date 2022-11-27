package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {
    FilmStorage filmStorage;
    Assertions assertions;

    @Autowired
    FilmService(FilmStorage filmStorage, Assertions assertions) {
        this.filmStorage = filmStorage;
        this.assertions = assertions;
    }

    public Collection<Film> getFilms() {
        return filmStorage.getFilms();
    }

    public Film addFilm(Film film) {
        return filmStorage.addFilm(film);
    }

    public Film updateFilm(Film film) {
        assertions.assertFilm(film.getId());
        return filmStorage.updateFilm(film);
    }

    public Film getFilm(int id) {
        assertions.assertFilm(id);
        return filmStorage.getFilm(id);
    }

    public void addLike(int filmId, int userId) {
        assertions.assertFilm(filmId);
        assertions.assertUser(userId);
        filmStorage.getFilm(filmId).getLikes().add(userId);
    }

    public void removeLike(int filmId, int userId) {
        assertions.assertFilm(filmId);
        assertions.assertUser(userId);
        filmStorage.getFilm(filmId).getLikes().remove(userId);
    }

    public List<Film> getMostPopularFilms(int count) {
        return filmStorage.getFilms().stream()
                .sorted((o1, o2) -> -1 * Integer.compare(o1.getLikes().size(), o2.getLikes().size()))
                .limit(count)
                .collect(Collectors.toList());

    }
}
