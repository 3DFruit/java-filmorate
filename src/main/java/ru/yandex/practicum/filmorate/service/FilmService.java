package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {
    FilmStorage filmStorage;

    @Autowired
    FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public Collection<Film> getFilms() {
        return filmStorage.getFilms();
    }

    public Film addFilm(Film film) {
        return filmStorage.addFilm(film);
    }

    public Film updateFilm(Film film) {
        return filmStorage.updateFilm(film);
    }

    public Film getFilm(int id) {
        Film film = filmStorage.getFilm(id);
        if (film == null) {
            throw new FilmNotFoundException("Не найден фильм с id - " + id);
        }
        return film;
    }

    public void addLike(int filmId, int userId) {
        Film film = filmStorage.getFilm(filmId);
        film.getLikes().add(userId);
    }

    public void removeLike(int filmId, int userId) {
        Film film = filmStorage.getFilm(filmId);
        film.getLikes().remove(userId);
    }

    public List<Film> getMostPopularFilms(int count) {
        return filmStorage.getFilms().stream()
                .sorted((o1, o2) -> -1 * Integer.compare(o1.getLikes().size(), o2.getLikes().size()))
                .limit(count)
                .collect(Collectors.toList());

    }
}
