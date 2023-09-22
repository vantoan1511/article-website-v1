package com.webtintuc.dao;

import com.webtintuc.model.User;

public interface IUserDao {
    User findById(Long id);

    User findByUsername(String username);

    User findByEmail(String email);

    User findByToken(String token);

    User save(User user);

    void update(User user);
}
