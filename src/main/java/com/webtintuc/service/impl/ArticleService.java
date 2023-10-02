package com.webtintuc.service.impl;

import com.webtintuc.dao.IArticleDao;
import com.webtintuc.dao.ICategoryDao;
import com.webtintuc.model.Article;
import com.webtintuc.model.User;
import com.webtintuc.service.IArticleService;
import com.webtintuc.service.IAuthService;
import com.webtintuc.sqlbuilder.Filter;
import com.webtintuc.sqlbuilder.Pageable;
import com.webtintuc.util.ListUtilImpl;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ArticleService implements IArticleService {

    @Inject
    private IArticleDao articleDao;

    @Inject
    private ICategoryDao categoryDao;


    @Override
    public List<Article> findAll() {
        return articleDao.findAll();
    }

    @Override
    public List<Article> findAll(Pageable pageable) {
        return articleDao.findAll(pageable);
    }

    @Override
    public List<Article> findByAuthorName(Pageable pageable, String authorName) {
        return articleDao.findByAuthorName(pageable, authorName);
    }

    @Override
    public List<Article> findByCategoryCode(Pageable pageable, String categoryCode) {
        return articleDao.findByCategoryCode(pageable, categoryCode);
    }

    @Override
    public List<Article> findByKeyword(Pageable pageable, String keyword) {
        return articleDao.findByKeyword(pageable, keyword);
    }

    @Override
    public List<Article> findToday(Pageable pageable) {
        return articleDao.findToday(pageable);
    }

    @Override
    public List<Article> findFromLastWeek(Pageable pageable) {
        return articleDao.findFromLastWeek(pageable);
    }

    @Override
    public List<Article> findFromLastMonth(Pageable pageable) {
        return articleDao.findFromLastMonth(pageable);
    }

    @Override
    public List<Article> findByFilters(Pageable pageable, Filter filter) {
        List<Article> articles = articleDao.findByFilters(pageable, filter);
        articles.forEach(item -> item.setCategory(categoryDao.findById(item.getCategoryId())));
        return articles;
    }

    @Override
    public Article findById(Long id) {
        return articleDao.findById(id);
    }

    @Override
    public Article create(Article article) {
        article.setViews(0);
        article.setCreatedDate(Timestamp.from(Instant.now()));
        article.setModifiedDate(article.getCreatedDate());
        return articleDao.findById(articleDao.save(article));
    }

    @Override
    public Article update(Article article) {
        Article oldArticle = articleDao.findById(article.getId());
        if (article.getViews() == null)
            article.setViews(oldArticle.getViews());
        article.setCreatedDate(oldArticle.getCreatedDate());
        article.setCreatedBy(oldArticle.getCreatedBy());
        article.setModifiedDate(Timestamp.from(Instant.now()));
        articleDao.update(article);
        return articleDao.findById(article.getId());
    }

    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            articleDao.delete(id);
        }
    }

    @Override
    public Integer countByFilters(Filter filter) {
        return findByFilters(new Pageable(1, 1000), filter).size();
    }

    @Override
    public Integer getTotalItems() {
        return articleDao.getTotalItem();
    }
}
