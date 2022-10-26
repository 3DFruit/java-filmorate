package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {

    static FilmController controller;

    @BeforeAll
    static void create(){
        controller = new FilmController();
    }

    @Test
    void addFilmTooLongDescription() {
        Film film  = new Film();
        film.setId(0);
        film.setName("film");
        film.setDescription("descriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescription"
                +"descriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescription"
                + "descriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescription");
        film.setReleaseDate(LocalDate.of(2000,1,1));
        film.setDuration(120);
        assertThrows(ValidationException.class, () -> controller.addFilm(film));
    }

    @Test
    void addFilmWrongReleaseDate() {
        Film film  = new Film();
        film.setId(0);
        film.setName("film");
        film.setDescription("description");
        film.setReleaseDate(Film.MIN_RELEASE_DATE.minusDays(1));
        film.setDuration(120);
        assertThrows(ValidationException.class, () -> controller.addFilm(film));
    }

    @Test
    void addFilmEmptyTitle() {
        Film film  = new Film();
        film.setId(0);
        film.setName("");
        film.setDescription("description");
        film.setReleaseDate(LocalDate.of(2000,1,1));
        film.setDuration(120);
        assertThrows(ValidationException.class, () -> controller.addFilm(film));
    }

    @Test
    void addFilmZeroDuration() {
        Film film  = new Film();
        film.setId(0);
        film.setName("film");
        film.setDescription("description");
        film.setReleaseDate(LocalDate.of(2000,1,1));
        film.setDuration(0);
        assertThrows(ValidationException.class, () -> controller.addFilm(film));
    }

    @Test
    void addFilm() {
        Film film  = new Film();
        film.setId(0);
        film.setName("film");
        film.setDescription("description");
        film.setReleaseDate(LocalDate.of(2000,1,1));
        film.setDuration(120);
        try {
            assertEquals(film, controller.addFilm(film));
        }
        catch (ValidationException e) {
            e.printStackTrace();
        }
    }
}