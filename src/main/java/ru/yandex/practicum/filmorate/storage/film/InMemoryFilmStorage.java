package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private int nextId = 1;
    private final Map<Integer, Film> films = new HashMap<>();
    @Override
    public Film addFilm(Film film) {
        if (film.getReleaseDate().isBefore(Film.MIN_RELEASE_DATE)) {
            throw new ValidationException("Неверная дата выпуска фильма");
        }
        film.setId(nextId);
        films.put(nextId, film);
        nextId++;
        return film;
    }

    @Override
    public void removeFilmById(int id) {
        films.remove(id);
    }

    @Override
    public Film updateFilm(Film film) {
        if (film.getReleaseDate().isBefore(Film.MIN_RELEASE_DATE)) {
            throw new ValidationException("Неверная дата выпуска фильма");
        }
        int id = film.getId();
        if (!films.containsKey(id)) {
            throw new FilmNotFoundException("Не найден фильм с id - " + id);
        }
        films.put(id, film);
        return film;
    }

    @Override
    public Collection<Film> getFilms() {
        return films.values();
    }

    @Override
    public Film getFilm(int id) {
        return films.get(id);
    }
}
