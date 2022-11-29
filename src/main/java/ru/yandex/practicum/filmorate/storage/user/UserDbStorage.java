package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.sql.Date;
import java.util.Objects;

@Component("UserDbStorage")
public class UserDbStorage implements UserStorage{

    private final JdbcTemplate jdbcTemplate;

    UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public User addUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        String sql = "insert into USERS(email, login, user_name, birthday) values (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sql, new String[]{"USER_ID"});
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getLogin());
            statement.setString(3,user.getName());
            statement.setDate(4, Date.valueOf(user.getBirthday()));
            return statement;
        }, keyHolder);
        int id = Objects.requireNonNull(keyHolder.getKey()).intValue();
        user.setId(id);
        return user;
    }

    @Override
    public void removeUserById(int id) {
        String sql = "delete from USERS where user_id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public User updateUser(User user) {
        String sql = "update users set email = ?, login = ?, user_name = ?, birthday = ? where user_id = ?";
        int result = jdbcTemplate.update(sql,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                Date.valueOf(user.getBirthday()),
                user.getId());
        if (result < 1) {
            throw new ObjectNotFoundException("Не найден пользователь с id - " + user.getId());
        }
        return user;
    }

    @Override
    public Collection<User> getUsers() {
        String sql = "select * from USERS order by USER_ID";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rowToUser(rs));
    }

    @Override
    public User getUser(int id) {
        String sql = "select * from USERS where USER_ID=?";
        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rowToUser(rs), id);
        }
        catch (EmptyResultDataAccessException e) {
            throw new ObjectNotFoundException("Не найден пользователь с id - " + id);
        }
    }

    private User rowToUser(ResultSet resultSet) throws SQLException {
        return User.builder()
                .id(resultSet.getInt("USER_ID"))
                .email(resultSet.getString("EMAIL"))
                .login(resultSet.getString("LOGIN"))
                .name(resultSet.getString("USER_NAME"))
                .birthday(resultSet.getDate("BIRTHDAY").toLocalDate())
                .build();
    }
}
