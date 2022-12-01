package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.InvalidParameterException;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.filmgenre.FilmGenreStorage;
import ru.yandex.practicum.filmorate.storage.like.LikeStorage;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
public class FilmService {
    FilmStorage filmStorage;
    LikeStorage likeStorage;
    FilmGenreStorage filmGenreStorage;

    @Autowired
    FilmService(@Qualifier("FilmDbStorage") FilmStorage filmStorage,
                @Qualifier("LikeDbStorage") LikeStorage likeStorage,
                @Qualifier("FilmGenreDbStorage") FilmGenreStorage filmGenreStorage) {
        this.filmStorage = filmStorage;
        this.likeStorage = likeStorage;
        this.filmGenreStorage = filmGenreStorage;
    }

    public Collection<Film> getFilms() {
        Collection<Film> collection = filmStorage.getFilms();
        for (Film film : collection) {
            film.setGenres(new TreeSet<>(filmGenreStorage.getGenresOfFilm(film.getId())));
        }
        return collection;
    }

    public Film addFilm(Film film) {
        Set<Genre> genreSet = film.getGenres();
        film = filmStorage.addFilm(film);
        film.setGenres(genreSet);
        filmGenreStorage.updateGenresOfFilm(film);
        film.setGenres(new TreeSet<>(filmGenreStorage.getGenresOfFilm(film.getId())));
        return film;
    }

    public Film updateFilm(Film film) {
        Set<Genre> genreSet = film.getGenres();
        film = filmStorage.updateFilm(film);
        film.setGenres(genreSet);
        filmGenreStorage.updateGenresOfFilm(film);
        film.setGenres(new TreeSet<>(filmGenreStorage.getGenresOfFilm(film.getId())));
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
        film.setGenres(new TreeSet<>(filmGenreStorage.getGenresOfFilm(id)));
        return film;
    }

    public List<Film> getMostPopularFilms(int count) {
        return getFilms().stream()
                .sorted((o1, o2) -> -1 * Integer.compare(likeStorage.getLikesQuantity(o1.getId()),
                        likeStorage.getLikesQuantity(o2.getId())))
                .limit(count)
                .collect(Collectors.toList());
    }
}
