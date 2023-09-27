package com.webtintuc.service;

import com.webtintuc.model.User;
import com.webtintuc.sqlbuilder.Pageable;

import java.util.List;

public interface IUserService {

    List<User> findAll(Pageable pageable);

    User findById(Long id);

    User findByUsername(String username);

    User findByEmail(String email);

    User findByToken(String token);

    User validate(String username, String password);

    User register(User user);

    User update(User user);

    void resetPassword(String email);

    Integer getTotalItems();
}
