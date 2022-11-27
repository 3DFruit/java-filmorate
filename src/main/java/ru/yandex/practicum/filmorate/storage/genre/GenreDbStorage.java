package ru.yandex.practicum.filmorate.storage.genre;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

@Component("GenreDbStorage")
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    public GenreDbStorage(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Collection<Genre> getGenres() {
        String sql = "select * from GENRES order by GENRE_ID";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeGenre(rs));

    }

    @Override
    public Genre getGenre(int id) {
        String sql = "select GENRE_NAME from GENRES where GENRE_ID=?";
        SqlRowSet genreRows = jdbcTemplate.queryForRowSet(sql, id);
        if (genreRows.next()) {
            String name = genreRows.getString("GENRE_NAME");
            return new Genre(id, name);
        }
        else {
            return null;
        }
    }

    private Genre makeGenre(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("genre_id");
        String name = rs.getString("genre_name");
        return new Genre(id, name);
    }
}
