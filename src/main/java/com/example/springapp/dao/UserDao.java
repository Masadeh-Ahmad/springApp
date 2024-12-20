package com.example.springapp.dao;

import com.example.springapp.model.Credentials;
import com.example.springapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
@Profile("database")
public class UserDao {

    private final JdbcTemplate jdbcTemplate;

    public UserDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    public User auth(Credentials credentials){
        final String sql = "SELECT * FROM users WHERE username=? AND password=?;";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        User user = jdbcTemplate.queryForObject(sql,  new UserRowMapper(), credentials.getUsername(), credentials.getPassword());
        if(user != null)
            return user;
        else
            return null;
    }
    private static final class UserRowMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int index) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            return user;
        }
    }
}