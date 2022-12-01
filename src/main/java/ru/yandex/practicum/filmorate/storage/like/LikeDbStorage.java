package ru.yandex.practicum.filmorate.storage.like;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component("LikeDbStorage")
public class LikeDbStorage implements LikeStorage {

    private final JdbcTemplate jdbcTemplate;

    LikeDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public void addLike(int filmId, int userId) {
        String sql = "insert into FILMS_LIKES ( FILM_ID, USER_ID ) " +
                "values ( ?, ? )";
        jdbcTemplate.update(sql, filmId, userId);
    }

    @Override
    public void removeLike(int filmId, int userId) {
        String sql = "delete from FILMS_LIKES where FILM_ID = ? and USER_ID = ?";
        jdbcTemplate.update(sql, filmId, userId);
    }

    @Override
    public Integer getLikesQuantity(int filmId) {
        String sql = "select count(FILM_LIKE_ID) as quantity from FILMS_LIKES where FILM_ID=?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rs.getInt("quantity"), filmId);
    }
}
