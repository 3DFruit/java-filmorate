package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.service.LikeService;

@RestController
@RequestMapping("/films/{id}/like/{userId}")
@Slf4j
public class FilmLikesController {
    LikeService likeService;

    @Autowired
    FilmLikesController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PutMapping
    public void addLike(@PathVariable int id, @PathVariable int userId) {
        log.info("Пользователь с id {} поставил лайк фильму с id {}", userId, id);
        likeService.addLike(id, userId);
    }

    @DeleteMapping
    public void removeLike(@PathVariable int id, @PathVariable int userId) {
        log.info("Пользователь с id {} убрал лайк у фильма с id {}", userId, id);
        likeService.removeLike(id, userId);
    }
}
