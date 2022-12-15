package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.InvalidParameterException;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.filmgenre.FilmGenreStorage;
import ru.yandex.practicum.filmorate.storage.like.LikeStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FilmService {
    FilmStorage filmStorage;
    LikeStorage likeStorage;
    FilmGenreStorage filmGenreStorage;

    @Autowired
    FilmService(FilmStorage filmStorage,
                LikeStorage likeStorage,
                FilmGenreStorage filmGenreStorage) {
        this.filmStorage = filmStorage;
        this.likeStorage = likeStorage;
        this.filmGenreStorage = filmGenreStorage;
    }

    public Collection<Film> getFilms() {
        Collection<Film> films = filmStorage.getFilms();
        setFilmGenres(films);
        return films;
    }

    public Film addFilm(Film film) {
        Set<Genre> genreSet = film.getGenres();
        film = filmStorage.addFilm(film);
        film.setGenres(genreSet);
        filmGenreStorage.updateGenresOfFilm(film);
        setFilmGenres(List.of(film));
        return film;
    }

    public Film updateFilm(Film film) {
        Set<Genre> genreSet = film.getGenres();
        film = filmStorage.updateFilm(film);
        if (film == null) {
            throw new ObjectNotFoundException("Не найден фильм" );
        }
        film.setGenres(genreSet);
        filmGenreStorage.updateGenresOfFilm(film);
        setFilmGenres(List.of(film));
        return film;
    }

    public Film getFilm(int id) {
        if (id < 1) {
            throw new InvalidParameterException("Неверные параметры запроса");
        }
        Film film = filmStorage.getFilm(id);
        if (film == null) {
            throw new ObjectNotFoundException("Не найден фильм с id - " + id);
        }
        setFilmGenres(List.of(film));
        return film;
    }

    public List<Film> getMostPopularFilms(int count) {
        List<Film> films = filmStorage.getPopularFilms(count);
        setFilmGenres(films);
        return films;
    }

    private void setFilmGenres(Collection<Film> films) {
        List<Integer> filmIds = films.stream().map(Film::getId).collect(Collectors.toList());
        Map<Integer, Set<Genre>> genres = filmGenreStorage.getGenresOfFilms(filmIds);
        for (Film film : films) {
            film.setGenres(genres.getOrDefault(film.getId(), new TreeSet<>()));
        }
    }
}
