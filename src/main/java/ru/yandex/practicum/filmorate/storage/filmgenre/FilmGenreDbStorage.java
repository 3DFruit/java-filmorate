package ru.yandex.practicum.filmorate.storage.filmgenre;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component("FilmGenreDbStorage")
public class FilmGenreDbStorage implements FilmGenreStorage {
    JdbcTemplate jdbcTemplate;

    FilmGenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<Genre> getGenresOfFilm(int filmId) {
        String genresSql = "select FG.GENRE_ID, G.GENRE_NAME " +
                "from FILMS_GENRES as FG " +
                "join GENRES as G on G.GENRE_ID = FG.GENRE_ID " +
                "where FG.FILM_ID=?";
        return jdbcTemplate.query(genresSql, (rs, rowNum) -> rowToGenre(rs), filmId);
    }

    @Override
    public void updateGenresOfFilm (Film film) {
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
    }
    private Genre rowToGenre(ResultSet resultSet) throws SQLException {
        return new Genre(resultSet.getInt("GENRE_ID"), resultSet.getString("GENRE_NAME"));
    }
}
