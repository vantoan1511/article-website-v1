package com.webtintuc.service.impl;

import com.webtintuc.dao.IArticleDao;
import com.webtintuc.model.Article;
import com.webtintuc.service.IArticleService;
import com.webtintuc.sqlbuilder.Filter;
import com.webtintuc.sqlbuilder.Pageable;
import com.webtintuc.util.ListUtilImpl;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class ArticleService implements IArticleService {

    @Inject
    IArticleDao articleDao;

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
    public List<Article> searchResults(Pageable pageable, String keyword, String dateFormat, String categoryCode) {
        List<Article> keywordResult = findByKeyword(pageable, keyword);
        List<Article> codeResult;
        List<Article> dateResult;

        codeResult = switch (categoryCode) {
            case "all" -> findAll();
            default -> findByCategoryCode(pageable, categoryCode);
        };

        dateResult = switch (dateFormat) {
            case "today" -> findToday(pageable);
            case "last-week" -> findFromLastWeek(pageable);
            case "last-month" -> findFromLastMonth(pageable);
            default -> findAll();
        };
        keywordResult = ListUtilImpl.intersect(keywordResult, codeResult);
        return ListUtilImpl.intersect(keywordResult, dateResult);
    }

    @Override
    public List<Article> findByFilters(Pageable pageable, Filter filter) {
        List<Article> keywordResult = findByKeyword(pageable, filter.getKeyword());
        List<Article> codeResult;
        List<Article> dateResult;

        codeResult = switch (filter.getCategoryCode()) {
            case "all" -> findAll();
            default -> findByCategoryCode(pageable, filter.getCategoryCode());
        };

        dateResult = switch (filter.getDateFormat()) {
            case "today" -> findToday(pageable);
            case "last-week" -> findFromLastWeek(pageable);
            case "last-month" -> findFromLastMonth(pageable);
            default -> findAll();
        };
        keywordResult = ListUtilImpl.intersect(keywordResult, codeResult);
        return ListUtilImpl.intersect(keywordResult, dateResult);
    }

    @Override
    public Article findById(Long id) {
        return articleDao.findById(id);
    }

    @Override
    public Article save(Article article) {
        return articleDao.findById(articleDao.save(article));
    }

    @Override
    public Article update(Article article) {
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
