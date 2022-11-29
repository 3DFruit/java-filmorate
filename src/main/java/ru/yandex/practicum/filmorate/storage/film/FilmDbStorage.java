package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component("FilmDbStorage")
public class FilmDbStorage implements FilmStorage {

    JdbcTemplate jdbcTemplate;

    FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public Film addFilm(Film film) {
        String sql = "insert into FILMS(TITLE, DESCRIPTION, DURATION, RELEASE_DATE, MPA_ID) values (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sql, new String[]{"FILM_ID"});
            statement.setString(1, film.getName());
            statement.setString(2, film.getDescription());
            statement.setLong(3, film.getDuration());
            statement.setDate(4, Date.valueOf(film.getReleaseDate()));
            statement.setInt(5, film.getMpa().getId());
            return statement;
        }, keyHolder);
        int id = Objects.requireNonNull(keyHolder.getKey()).intValue();
        if (film.getGenres() != null && film.getGenres().size() > 0) {
            StringBuilder genresSqlBuilder = new StringBuilder("insert into FILMS_GENRES(FILM_ID, GENRE_ID) ");
            List<Integer> args = new ArrayList<>();
            for (Genre genre : film.getGenres()) {
                genresSqlBuilder.append("values (?, ?),");
                args.add(id);
                args.add(genre.getId());
            }
            genresSqlBuilder.replace(genresSqlBuilder.length() - 1, genresSqlBuilder.length(), "");
            jdbcTemplate.update(genresSqlBuilder.toString(), args.toArray());
        }
        film.setId(id);
        return film;
    }

    @Override
    public void removeFilmById(int id) {
        String sql = "delete from FILMS where FILM_ID = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Film updateFilm(Film film) {
        String sql = "update FILMS " +
                "set TITLE = ?, DESCRIPTION = ?, DURATION = ?, RELEASE_DATE = ?, MPA_ID = ? " +
                "where FILM_ID = ?";
        int result = jdbcTemplate.update(sql,
                film.getName(),
                film.getDescription(),
                film.getDuration(),
                Date.valueOf(film.getReleaseDate()),
                film.getMpa().getId(),
                film.getId());
        if (result < 1) {
            throw new ObjectNotFoundException("Не найден фильм с id - " + film.getId());
        }
        jdbcTemplate.update("delete from FILMS_GENRES where FILM_ID=?",film.getId());
        if (film.getGenres() != null && film.getGenres().size() > 0) {
            StringBuilder genresSqlBuilder = new StringBuilder("merge into FILMS_GENRES(FILM_ID, GENRE_ID) " +
                    "key(FILM_ID, GENRE_ID) values");
            List<Integer> args = new ArrayList<>();
            for (Genre genre : film.getGenres()) {
                genresSqlBuilder.append("(?, ?),");
                args.add(film.getId());
                args.add(genre.getId());
            }
            genresSqlBuilder.replace(genresSqlBuilder.length() - 1, genresSqlBuilder.length(), "");
            jdbcTemplate.update(genresSqlBuilder.toString(), args.toArray());
        }
        return getFilm(film.getId());
    }

    @Override
    public Collection<Film> getFilms() {
        String sql = "select F.FILM_ID, F.TITLE, F.DESCRIPTION, F.DURATION, F.RELEASE_DATE, F.MPA_ID, M.MPA_NAME " +
                "from FILMS as F " +
                "join MPA as M on F.MPA_ID = M.MPA_ID " +
                "order by FILM_ID";
        List<Film> films = jdbcTemplate.query(sql, (rs, rowNum) -> rowToFilm(rs));
        for (Film film : films) {
            String genresSql = "select FG.GENRE_ID, G.GENRE_NAME " +
                    "from FILMS_GENRES as FG " +
                    "join GENRES as G on G.GENRE_ID = FG.GENRE_ID " +
                    "where FG.FILM_ID=?";
            film.setGenres(new HashSet<>(jdbcTemplate.query(genresSql, (rs, rowNum) -> rowToGenre(rs), film.getId())));
        }
        return films;
    }

    @Override
    public Film getFilm(int id) {
        String sql = "select F.FILM_ID, F.TITLE, F.DESCRIPTION, F.DURATION, F.RELEASE_DATE, F.MPA_ID, M.MPA_NAME " +
                "from FILMS as F " +
                "join MPA as M on F.MPA_ID = M.MPA_ID " +
                "where F.FIlM_ID=?";
        try {
            Film film = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rowToFilm(rs), id);
            if (film != null) {
                String genresSql = "select FG.GENRE_ID, G.GENRE_NAME " +
                        "from FILMS_GENRES as FG " +
                        "join GENRES as G on G.GENRE_ID = FG.GENRE_ID " +
                        "where FG.FILM_ID=?";
                film.setGenres(new TreeSet<>(jdbcTemplate.query(genresSql, (rs, rowNum) -> rowToGenre(rs), id)));
            }
            return film;
        }
        catch (EmptyResultDataAccessException e) {
            throw new ObjectNotFoundException("Не найден фильм с id - " + id);
        }
    }

    private Film rowToFilm(ResultSet resultSet) throws SQLException {
        return Film.builder()
                .id(resultSet.getInt("FILM_ID"))
                .name(resultSet.getString("TITLE"))
                .description(resultSet.getString("DESCRIPTION"))
                .duration(resultSet.getLong("DURATION"))
                .releaseDate(resultSet.getDate("RELEASE_DATE").toLocalDate())
                .mpa(new Mpa(resultSet.getInt("MPA_ID"), resultSet.getString("MPA_NAME")))
                .build();
    }

    private Genre rowToGenre(ResultSet resultSet) throws SQLException {
        return new Genre(resultSet.getInt("GENRE_ID"), resultSet.getString("GENRE_NAME"));
    }
}
