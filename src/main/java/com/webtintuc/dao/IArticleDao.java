package com.webtintuc.dao;

import com.webtintuc.model.Article;
import com.webtintuc.sqlbuilder.Filter;
import com.webtintuc.sqlbuilder.Pageable;

import java.util.List;

public interface IArticleDao {
    List<Article> findAll();

    List<Article> findAll(Pageable pageable);

    List<Article> findByKeyword(Pageable pageable, String keyword);

    List<Article> findByCategoryCode(Pageable pageable, String categoryCode);

    List<Article> findByAuthorName(Pageable pageable, String authorName);

    Article findById(Long id);

    List<Article> findToday(Pageable pageable);

    List<Article> findFromLastWeek(Pageable pageable);

    List<Article> findFromLastMonth(Pageable pageable);

    Long save(Article article);

    void update(Article article);

    void delete(Long id);

    Integer getTotalItem();


}
