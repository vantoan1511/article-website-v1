package com.webtintuc.dao.impl;

import com.webtintuc.dao.ICategoryDao;
import com.webtintuc.mapper.CategoryMapper;
import com.webtintuc.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryDao extends JdbcTemplateImpl<Category> implements ICategoryDao {
    String sql;
    List<Category> categories = new ArrayList<>();

    @Override
    public List<Category> findAll() {
        sql = "SELECT * FROM category ";
        return query(sql, new CategoryMapper());
    }

    @Override
    public Category findById(Long id) {
        sql = "SELECT * FROM category WHERE id=?";
        categories = query(sql, new CategoryMapper(), id);
        return categories.isEmpty() ? null : categories.get(0);
    }

    @Override
    public Category findByCode(String code) {
        sql = "SELECT * FROM category WHERE code=?";
        categories = query(sql, new CategoryMapper(), code);
        return categories.isEmpty() ? null : categories.get(0);
    }

    @Override
    public Category findByName(String name) {
        sql = "SELECT * FROM category WHERE name=?";
        categories = query(sql, new CategoryMapper(), name);
        return categories.isEmpty() ? null : categories.get(0);
    }

    @Override
    public Long save(Category category) {
        sql = "INSERT INTO category(code, name) VALUES(?, ?)";
        return insert(sql, category.getCode(), category.getName());
    }

    @Override
    public void update(Category category) {
        sql = "UPDATE category SET code=?, name=? WHERE id=?";
        update(sql, category.getCode(), category.getName(), category.getId());
    }

    @Override
    public void delete(Long id) {
        sql = "DELETE FROM category WHERE id=?";
        update(sql, id);
    }
}
