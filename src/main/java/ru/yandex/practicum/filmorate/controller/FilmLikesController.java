package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.service.FilmService;

@RestController
@RequestMapping("/films/{id}/like/{userId}")
public class FilmLikesController {
    FilmService filmService;

    @Autowired
    FilmLikesController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PutMapping
    public void addLike(@PathVariable int id, @PathVariable int userId) {
        filmService.addLike(id, userId);
    }

    @DeleteMapping
    public void removeLike(@PathVariable int id, @PathVariable int userId) {
        filmService.removeLike(id, userId);
    }
}
