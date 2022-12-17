package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    FilmService filmService;

    @Autowired
    FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public Collection<Film> getFilms() {
        log.info("Запрошены все фильмы");
        return filmService.getFilms();
    }

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        film = filmService.addFilm(film);
        log.info("Добавлен фильм с id {}", film.getId());
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        film = filmService.updateFilm(film);
        log.info("Обновлен фильм с id {}", film.getId());
        return film;
    }

    @GetMapping("/{id}")
    public Film getFilm(@PathVariable int id) {
        log.info("Запрошен фильм с id {}", id);
        return filmService.getFilm(id);
    }

    @GetMapping("popular")
    public List<Film> getMostPopularFilms(@RequestParam(defaultValue = "10") int count){
        log.info("Запрошены {} самых популярных фильмов", count);
        return filmService.getMostPopularFilms(count);
    }
}
