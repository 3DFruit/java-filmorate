package ru.yandex.practicum.filmorate.storage.friendship;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

@Component
public class FriendshipDbStorage implements FriendshipStorage {
    private final JdbcTemplate jdbcTemplate;

    FriendshipDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public Collection<User> getFriends(int userId) {
        String sql = "select * from FRIENDSHIPS as FS " +
                "join USERS as U on FS.RECEIVER_USER_ID = U.USER_ID " +
                "where FS.SENDER_USER_ID = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rowToUser(rs), userId);
    }

    @Override
    public void addToFriends(int userId, int friendId) {
        String sql = "insert into FRIENDSHIPS ( SENDER_USER_ID, RECEIVER_USER_ID, STATUS_ID ) " +
                "values ( ?, ?, 1 )";
        jdbcTemplate.update(sql, userId, friendId);
    }

    @Override
    public void removeFromFriends(int userId, int friendId) {
        String sql = "delete from FRIENDSHIPS where SENDER_USER_ID in (? , ?) and RECEIVER_USER_ID in (?, ?)";
        jdbcTemplate.update(sql, userId, friendId, userId, friendId);
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
