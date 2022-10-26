package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private final Map<Integer, Film> films = new HashMap<>();

    @GetMapping
    public Collection<Film> getFilms() {
        return films.values();
    }

    @PostMapping
    public Film addFilm(@RequestBody Film film) throws ValidationException {
        if (!validate(film)) {
            log.debug("Не пройдена валиданция данных о фильме");
            throw new ValidationException("Ошибка добавления фильма, данные не прошли валидацию");
        }
        films.put(film.getId(), film);
        log.debug("Добавлен фильм с id {}", film.getId());
        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) throws ValidationException {
        if (!validate(film)) {
            log.debug("Не пройдена валиданция данных о фильме");
            throw new ValidationException("Ошибка обновления фильма, данные не прошли валидацию");
        }
        films.put(film.getId(), film);
        log.debug("Обновлен фильм с id {}", film.getId());
        return film;
    }

    private boolean validate(Film film) {
        return !film.getName().isBlank()
                && film.getDescription().length() <= 200
                && !film.getReleaseDate().isBefore(Film.MIN_RELEASE_DATE)
                && film.getDuration() > 0;
    }
}
