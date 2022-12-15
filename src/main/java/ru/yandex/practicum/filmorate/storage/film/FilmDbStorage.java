package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
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
            return null;
        }
        return getFilm(film.getId());
    }

    @Override
    public Collection<Film> getFilms() {
        String sql = "select F.FILM_ID, F.TITLE, F.DESCRIPTION, F.DURATION, F.RELEASE_DATE, F.MPA_ID, M.MPA_NAME " +
                "from FILMS as F " +
                "join MPA as M on F.MPA_ID = M.MPA_ID " +
                "order by FILM_ID";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rowToFilm(rs));
    }

    @Override
    public Film getFilm(int id) {
        String sql = "select F.FILM_ID, F.TITLE, F.DESCRIPTION, F.DURATION, F.RELEASE_DATE, F.MPA_ID, M.MPA_NAME " +
                "from FILMS as F " +
                "join MPA as M on F.MPA_ID = M.MPA_ID " +
                "where F.FIlM_ID=?";
        List<Film> queryResult = jdbcTemplate.query(sql, (rs, rowNum) -> rowToFilm(rs), id);
        if (queryResult.size() != 1) {
            return null;
        }
        return queryResult.get(0);
    }

    @Override
    public List<Film> getPopularFilms(int limit) {
        String sql = "select F.FILM_ID, F.TITLE, F.DESCRIPTION, F.DURATION, F.RELEASE_DATE, F.MPA_ID, M.MPA_NAME " +
                "from FILMS as F " +
                "join MPA as M on F.MPA_ID = M.MPA_ID " +
                "left join FILMS_LIKES as FL on F.FILM_ID = FL.FILM_ID " +
                "group by F.FILM_ID, F.TITLE, F.DESCRIPTION, F.DURATION, F.RELEASE_DATE, F.MPA_ID, M.MPA_NAME " +
                "order by COUNT(FL.FILM_LIKE_ID) desc " +
                "limit ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rowToFilm(rs), limit);
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
}
