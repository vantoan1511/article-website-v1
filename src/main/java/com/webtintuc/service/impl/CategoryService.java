package com.webtintuc.service.impl;

import com.webtintuc.dao.ICategoryDao;
import com.webtintuc.model.Category;
import com.webtintuc.service.ICategoryService;

import javax.inject.Inject;
import java.util.List;

public class CategoryService implements ICategoryService {

    @Inject
    private ICategoryDao categoryDao;

    @Override
    public List<Category> findAll() {
        return categoryDao.findAll();
    }

    @Override
    public Category findById(Long id) {
        return categoryDao.findById(id);
    }

    @Override
    public Category findByCode(String code) {
        return categoryDao.findByCode(code);
    }

    @Override
    public Category findByName(String name) {
        return categoryDao.findByName(name);
    }

    @Override
    public Category save(Category category) {
        return categoryDao.findById(categoryDao.save(category));
    }

    @Override
    public Category update(Category category) {
        categoryDao.update(category);
        return categoryDao.findById(category.getId());
    }

    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            categoryDao.delete(id);
        }
    }
}
