package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.service.FilmService;

@RestController
@RequestMapping("/films/{id}/like/{userId}")
@Slf4j
public class FilmLikesController {
    FilmService filmService;

    @Autowired
    FilmLikesController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PutMapping
    public void addLike(@PathVariable int id, @PathVariable int userId) {
        log.info("Пользователь с id {} поставил лайк фильму с id {}", userId, id);
        filmService.addLike(id, userId);
    }

    @DeleteMapping
    public void removeLike(@PathVariable int id, @PathVariable int userId) {
        log.info("Пользователь с id {} убрал лайк у фильма с id {}", userId, id);
        filmService.removeLike(id, userId);
    }
}
