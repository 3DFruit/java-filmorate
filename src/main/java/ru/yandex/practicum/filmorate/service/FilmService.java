package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.InvalidParameterException;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.like.LikeStorage;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {
    FilmStorage filmStorage;
    LikeStorage likeStorage;

    @Autowired
    FilmService(@Qualifier("FilmDbStorage") FilmStorage filmStorage,
                @Qualifier("LikeDbStorage") LikeStorage likeStorage) {
        this.filmStorage = filmStorage;
        this.likeStorage = likeStorage;
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
        if (id < 1) {
            throw new InvalidParameterException("Неверные параметры запроса");
        }
        Film film = filmStorage.getFilm(id);
        if (film == null) {
            throw new ObjectNotFoundException("Не найден фильм с id - " + id);
        }
        return film;
    }

    public List<Film> getMostPopularFilms(int count) {
        return filmStorage.getFilms().stream()
                .sorted((o1, o2) -> -1 * Integer.compare(likeStorage.getLikesQuantity(o1.getId()),
                        likeStorage.getLikesQuantity(o2.getId())))
                .limit(count)
                .collect(Collectors.toList());
    }
}
