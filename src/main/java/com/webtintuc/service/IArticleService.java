package com.webtintuc.service;

import com.webtintuc.model.Article;
import com.webtintuc.sqlbuilder.Filter;
import com.webtintuc.sqlbuilder.Pageable;

import java.util.List;

public interface IArticleService {
    List<Article> findAll();

    List<Article> findAll(Pageable pageable);

    List<Article> findByKeyword(Pageable pageable, String keyword);

    List<Article> findByCategoryCode(Pageable pageable, String categoryCode);

    List<Article> findByAuthorName(Pageable pageable, String authorName);

    List<Article> findToday(Pageable pageable);

    List<Article> findFromLastWeek(Pageable pageable);

    List<Article> findFromLastMonth(Pageable pageable);

    List<Article> findByFilters(Pageable pageable, Filter filter);

    Article findById(Long id);

    Article create(Article article);

    Article update(Article article);

    void delete(Long[] ids);

    Integer countByFilters(Filter filter);

    Integer getTotalItems();
}
