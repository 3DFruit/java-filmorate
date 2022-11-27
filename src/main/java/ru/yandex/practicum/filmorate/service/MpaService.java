package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;

import java.util.Collection;

@Service
public class MpaService {
    MpaStorage mpaStorage;

    @Autowired
    MpaService(@Qualifier("MpaDbStorage") MpaStorage mpaStorage) {
        this.mpaStorage = mpaStorage;
    }
    public Collection<Mpa> getMpa() {
        return mpaStorage.getMpa();
    }

    public Mpa getMpa(int id) {
        Mpa mpa = mpaStorage.getMpa(id);
        if (mpa == null) {
            throw new ObjectNotFoundException("Не найден жанр с id - " + id);
        }
        return mpa;
    }
}
