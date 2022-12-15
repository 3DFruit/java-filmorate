package ru.yandex.practicum.filmorate.storage.mpa;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

@Component
public class MpaDbStorage implements MpaStorage{
    private final JdbcTemplate jdbcTemplate;

    public MpaDbStorage(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Collection<Mpa> getMpa() {
        String sql = "select * from MPA order by MPA_ID";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeMpa(rs));

    }

    @Override
    public Mpa getMpa(int id) {
        String sql = "select * from MPA where MPA_ID=?";
        List<Mpa> queryResult = jdbcTemplate.query(sql, (rs, rowNum) -> makeMpa(rs), id);
        if (queryResult.size() != 1) {
            return null;
        }
        return queryResult.get(0);
    }

    private Mpa makeMpa(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("mpa_id");
        String name = rs.getString("mpa_name");
        return new Mpa(id, name);
    }
}
