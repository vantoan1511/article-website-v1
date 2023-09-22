package com.webtintuc.mapper;

import com.webtintuc.model.Category;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryMapper implements RowMapper<Category> {
    @Override
    public Category mapRow(ResultSet set) {
        Category category = new Category();
        try {
            category.setId(set.getLong("id"));
            category.setCode(set.getString("code"));
            category.setName(set.getString("name"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return category;
    }
}
