package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;

import java.util.Collection;

@Service
public class GenreService {

    GenreStorage genreStorage;

    @Autowired
    GenreService(@Qualifier("GenreDbStorage") GenreStorage genreStorage) {
        this.genreStorage = genreStorage;
    }
    public Collection<Genre> getGenres() {
        return genreStorage.getGenres();
    }

    public Genre getGenre(int id) {
        Genre genre = genreStorage.getGenre(id);
        if (genre == null) {
            throw new ObjectNotFoundException("Не найден жанр с id - " + id);
        }
        return genre;
    }
}
