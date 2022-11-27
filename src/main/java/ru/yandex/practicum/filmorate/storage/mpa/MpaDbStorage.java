package ru.yandex.practicum.filmorate.storage.mpa;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

@Component("MpaDbStorage")
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
        String sql = "select MPA_NAME from MPA where MPA_ID=?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> makeMpa(rs), id);
    }

    private Mpa makeMpa(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("mpa_id");
        String name = rs.getString("mpa_name");
        return new Mpa(id, name);
    }
}
