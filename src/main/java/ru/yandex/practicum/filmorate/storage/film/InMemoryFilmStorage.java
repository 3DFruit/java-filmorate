package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component("InMemoryFilmStorage")
public class InMemoryFilmStorage implements FilmStorage {
    private int nextId = 1;
    private final Map<Integer, Film> films = new HashMap<>();
    @Override
    public Film addFilm(Film film) {
        film.setId(nextId);
        films.put(nextId, film);
        nextId++;
        return film;
    }

    @Override
    public void removeFilmById(int id) {
        if(!films.containsKey(id)) {
            throw new ObjectNotFoundException("Пользователь не найден");
        }
        films.remove(id);
    }

    @Override
    public Film updateFilm(Film film) {
        int id = film.getId();
        if(!films.containsKey(id)) {
            throw new ObjectNotFoundException("Пользователь не найден");
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
