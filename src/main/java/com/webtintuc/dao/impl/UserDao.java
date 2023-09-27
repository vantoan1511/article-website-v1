package com.webtintuc.dao.impl;

import com.webtintuc.dao.IUserDao;
import com.webtintuc.mapper.UserMapper;
import com.webtintuc.model.User;
import com.webtintuc.sqlbuilder.Pageable;
import com.webtintuc.sqlbuilder.SQLBuilder;

import java.util.ArrayList;
import java.util.List;

public class UserDao extends JdbcTemplateImpl<User> implements IUserDao {
    String sql;
    List<User> users = new ArrayList<>();

    @Override
    public List<User> findAll(Pageable pageable) {
        sql = "SELECT * FROM user ";
        sql = SQLBuilder.build(sql, pageable);
        return query(sql, new UserMapper());
    }

    @Override
    public User findById(Long id) {
        sql = "SELECT * FROM user WHERE id=?";
        users = query(sql, new UserMapper(), id);
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public User findByUsername(String username) {
        sql = "SELECT * FROM user WHERE username=?";
        users = query(sql, new UserMapper(), username);
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public User findByEmail(String email) {
        sql = "SELECT * FROM user WHERE email=?";
        users = query(sql, new UserMapper(), email);
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public User findByToken(String token) {
        sql = "SELECT * FROM user WHERE token=?";
        users = query(sql, new UserMapper(), token);
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public User save(User user) {
        sql = "INSERT INTO user(fullname, email, username, password) ";
        sql += "VALUES(?, ?, ?, ?)";
        Long id = insert(sql, user.getFullname(), user.getEmail(), user.getUsername(), user.getPassword());
        return findById(id);
    }

    @Override
    public void update(User user) {
        sql = "UPDATE user SET fullname=?, email=?, username=?, password=?, avatar=?, token=?, roleid=? ";
        sql += "WHERE id=?";
        update(sql, user.getFullname(), user.getEmail(), user.getUsername(), user.getPassword(), user.getAvatar(),
                user.getToken(), user.getRoleId(), user.getId());
    }

    @Override
    public Integer countAll() {
        sql = "SELECT count(*) FROM user ";
        return count(sql);
    }
}
