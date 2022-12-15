package ru.yandex.practicum.filmorate.storage.filmgenre;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class FilmGenreDbStorage implements FilmGenreStorage {
    JdbcTemplate jdbcTemplate;

    FilmGenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void updateGenresOfFilm (Film film) {
        jdbcTemplate.update("delete from FILMS_GENRES where FILM_ID=?",film.getId());
        if (film.getGenres() != null && film.getGenres().size() > 0) {
            jdbcTemplate.batchUpdate("merge into FILMS_GENRES(FILM_ID, GENRE_ID) " +
                            "key(FILM_ID, GENRE_ID) values (?, ?)",
                    film.getGenres(),
                    100,
                    (PreparedStatement ps, Genre genre) -> {
                        ps.setInt(1, film.getId());
                        ps.setInt(2, genre.getId());
                    }
            );
        }
    }
    @Override
    public Map<Integer, Set<Genre>> getGenresOfFilms(List<Integer> filmIds) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        SqlParameterSource parameters = new MapSqlParameterSource("filmIds", filmIds);

        String genresSql = "select FG.FILM_ID, FG.GENRE_ID, G.GENRE_NAME " +
                "from FILMS_GENRES as FG " +
                "join GENRES as G on G.GENRE_ID = FG.GENRE_ID " +
                "where FG.FILM_ID in (:filmIds)";
        return namedParameterJdbcTemplate.query(genresSql,
                parameters,
                this::resultsToMap);
    }

    private Map<Integer, Set<Genre>> resultsToMap(ResultSet resultSet) throws SQLException {
        HashMap<Integer, Set<Genre>> queryResults = new HashMap<>();
        while (resultSet.next()) {
            int filmId = resultSet.getInt("FILM_ID");
            int genreId = resultSet.getInt("GENRE_ID");
            String genreName = resultSet.getString("GENRE_NAME");
            if (!queryResults.containsKey(filmId)) {
                queryResults.put(filmId, new TreeSet<>());
            }
            queryResults.get(filmId).add(new Genre(genreId, genreName));
        }
        return queryResults;
    }
}
