package com.webtintuc.service;

import com.webtintuc.model.Category;

import java.util.List;

public interface ICategoryService {
    List<Category> findAll();

    Category findById(Long id);

    Category findByCode(String code);

    Category findByName(String name);

    Category save(Category category);

    Category update(Category category);

    void delete(Long[] ids);
}
