package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private int nextId = 1;
    private final Map<Integer, Film> films = new HashMap<>();

    @GetMapping
    public Collection<Film> getFilms() {
        return films.values();
    }

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) throws ValidationException {
        if (film.getReleaseDate().isBefore(Film.MIN_RELEASE_DATE)) {
            log.debug("Дата выпуска фильма не может быть раньше {}",
                    Film.MIN_RELEASE_DATE.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
            );
            throw new ValidationException("Данные о фильме не прошли валидацию");
        }
        film.setId(nextId);
        films.put(nextId, film);
        nextId++;
        log.debug("Добавлен фильм с id {}", film.getId());
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) throws ValidationException {
        if (film.getReleaseDate().isBefore(Film.MIN_RELEASE_DATE)) {
            log.debug("Дата выпуска фильма не может быть раньше {}",
                    Film.MIN_RELEASE_DATE.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
            );
            throw new ValidationException("Данные о фильме не прошли валидацию");
        }
        int id = film.getId();
        if (!films.containsKey(id)) {
            log.debug("Фильм с id {} не найден", id);
            throw new ValidationException("Не удалось обновить данные о фильме");
        }
        films.put(id, film);
        log.debug("Обновлен фильм с id {}", id);
        return film;
    }
}
