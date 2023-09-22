package com.webtintuc.mapper;

import com.webtintuc.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet set) {
        User user = new User();
        try {
            user.setId(set.getLong("id"));
            user.setEmail(set.getString("email"));
            user.setFullname(set.getString("fullname"));
            user.setUsername(set.getString("username"));
            user.setPassword(set.getString("password"));
            user.setRoleId(set.getLong("roleid"));
            if (set.getString("token") != null)
                user.setToken(set.getString("token"));
            if (set.getString("avatar") != null)
                user.setAvatar(set.getString("avatar"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
