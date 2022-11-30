package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.Collection;

@RestController
@RequestMapping("/mpa")
@Slf4j
public class MpaController {
    MpaService mpaService;

    @Autowired
    MpaController(MpaService mpaService) {
        this.mpaService = mpaService;
    }

    @GetMapping
    public Collection<Mpa> getMpa() {
        log.info("Запрошен список жанров");
        return mpaService.getMpa();
    }

    @GetMapping("/{id}")
    public Mpa getGenre(@PathVariable int id) {
        log.info("Запрошен жанр с id {}", id);
        return mpaService.getMpa(id);
    }
}
