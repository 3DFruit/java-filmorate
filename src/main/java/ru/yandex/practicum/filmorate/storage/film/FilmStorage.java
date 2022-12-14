package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.List;

public interface FilmStorage {
    Film addFilm(Film film);
    void removeFilmById(int id);
    Film updateFilm(Film film);
    Collection<Film> getFilms();
    Film getFilm(int id);

    List<Film> getPopularFilms(int limit);
}
