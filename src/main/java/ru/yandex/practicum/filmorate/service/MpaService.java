package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.InvalidParameterException;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;

import java.util.Collection;

@Service
public class MpaService {
    MpaStorage mpaStorage;

    @Autowired
    MpaService(MpaStorage mpaStorage) {
        this.mpaStorage = mpaStorage;
    }
    public Collection<Mpa> getMpa() {
        return mpaStorage.getMpa();
    }

    public Mpa getMpa(int id) {
        if (id < 1) {
            throw new InvalidParameterException("Неверные параметры запроса");
        }
        Mpa mpa = mpaStorage.getMpa(id);
        if (mpa == null) {
            throw new ObjectNotFoundException("Не найден жанр с id - " + id);
        }
        return mpa;
    }
}
