package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Objects;

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
            statement.setInt(5, film.getMpaId());
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
        jdbcTemplate.update(sql,
                film.getName(),
                film.getDescription(),
                film.getDuration(),
                Date.valueOf(film.getReleaseDate()),
                film.getMpaId(),
                film.getId());
        return film;
    }

    @Override
    public Collection<Film> getFilms() {
        String sql = "select * from FILMS order by RELEASE_DATE desc";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rowToFilm(rs));
    }

    @Override
    public Film getFilm(int id) {
        String sql = "select * from FILMS where FIlM_ID=?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rowToFilm(rs), id);
    }

    private Film rowToFilm(ResultSet resultSet) throws SQLException {
        return Film.builder()
                .id(resultSet.getInt("FILM_ID"))
                .name(resultSet.getString("TITLE"))
                .description(resultSet.getString("DESCRIPTION"))
                .duration(resultSet.getLong("DURATION"))
                .releaseDate(resultSet.getDate("RELEASE_DATE").toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate())
                .mpaId(resultSet.getInt("MPA_ID"))
                .build();
    }
}
