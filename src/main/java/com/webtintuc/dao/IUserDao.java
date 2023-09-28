package com.webtintuc.dao;

import com.webtintuc.model.User;
import com.webtintuc.sqlbuilder.Pageable;

import java.util.List;

public interface IUserDao {

    List<User> findAll(Pageable pageable);
    User findById(Long id);

    User findByUsername(String username);

    User findByEmail(String email);

    User findByToken(String token);

    User save(User user);

    void update(User user);

    Integer countAll();

    void delete(Long id);
}
