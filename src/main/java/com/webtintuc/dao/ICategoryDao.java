package com.webtintuc.dao;

import com.webtintuc.model.Category;
import com.webtintuc.model.Role;

import java.util.List;

public interface ICategoryDao {

    List<Category> findAll();

    Category findById(Long id);

    Category findByCode(String code);

    Category findByName(String name);

    Long save(Category category);

    void update(Category category);

    void delete(Long id);
}
