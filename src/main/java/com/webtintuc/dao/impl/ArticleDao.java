package com.webtintuc.dao.impl;

import com.webtintuc.dao.IArticleDao;
import com.webtintuc.mapper.ArticleMapper;
import com.webtintuc.model.Article;
import com.webtintuc.sqlbuilder.Filter;
import com.webtintuc.sqlbuilder.Pageable;
import com.webtintuc.sqlbuilder.SQLBuilder;

import java.util.ArrayList;
import java.util.List;

public class ArticleDao extends JdbcTemplateImpl<Article> implements IArticleDao {
    String sql;
    List<Article> articles = new ArrayList<>();

    @Override
    public List<Article> findAll() {
        sql = "SELECT * FROM article";
        return query(sql, new ArticleMapper());
    }

    @Override
    public List<Article> findAll(Pageable pageable) {
        sql = SQLBuilder.build("SELECT * FROM article ", pageable);
        return query(sql, new ArticleMapper());
    }

    @Override
    public List<Article> findByKeyword(Pageable pageable, String keyword) {
        sql = SQLBuilder.build("SELECT * FROM article WHERE title LIKE '%"
                + keyword + "%' OR content LIKE '%" + keyword + "%' ", pageable);
        return query(sql, new ArticleMapper());
    }

    @Override
    public List<Article> findByCategoryCode(Pageable pageable, String categoryCode) {
        sql = "SELECT * FROM article a INNER JOIN category c ON a.categoryid=c.id WHERE code=? ";
        sql = SQLBuilder.build(sql, pageable);
        return query(sql, new ArticleMapper(), categoryCode);
    }

    @Override
    public List<Article> findByAuthorName(Pageable pageable, String authorName) {
        sql = SQLBuilder.build("SELECT * FROM article WHERE createdby=? ", pageable);
        return query(sql, new ArticleMapper(), authorName);
    }

    @Override
    public Article findById(Long id) {
        sql = "SELECT * FROM article WHERE id=?";
        articles = query(sql, new ArticleMapper(), id);
        return articles.isEmpty() ? null : articles.get(0);
    }

    @Override
    public List<Article> findToday(Pageable pageable) {
        sql = "SELECT * FROM article WHERE DATE(createddate)=CURRENT_DATE() ";
        sql = SQLBuilder.build(sql, pageable);
        return query(sql, new ArticleMapper());
    }

    @Override
    public List<Article> findFromLastWeek(Pageable pageable) {
        sql = "SELECT * FROM article WHERE createddate >= " +
                "DATE_SUB(CURRENT_DATE, INTERVAL DAYOFWEEK(CURRENT_DATE)+6 DAY) " +
                "  AND createddate < DATE_SUB(CURRENT_DATE, INTERVAL DAYOFWEEK(CURRENT_DATE)-2 DAY) ";
        sql = SQLBuilder.build(sql, pageable);
        return query(sql, new ArticleMapper());
    }

    @Override
    public List<Article> findFromLastMonth(Pageable pageable) {
        sql = "SELECT * FROM article WHERE createddate >= " +
                "CONCAT(YEAR(NOW()), '-', MONTH(NOW()) - 1, '-01 00:00:00') " +
                "AND createddate < CONCAT(YEAR(NOW()), '-', MONTH(NOW()), '-01 00:00:00') ";
        sql = SQLBuilder.build(sql, pageable);
        return query(sql, new ArticleMapper());
    }

    public List<Article> findByFilters(Pageable pageable, Filter filter) {
        sql = "SELECT * FROM article a INNER JOIN category c ON ";
        sql += "a.categoryid=c.id WHERE (title LIKE '%" + filter.getKeyword() + "%' ";
        sql += "OR content LIKE '%" + filter.getKeyword() + "%') ";
        sql += "AND createddate >= CASE ";
        sql += "WHEN ?='today' THEN CURRENT_DATE() ";
        sql += "WHEN ?='last-week' THEN DATE_SUB(CURDATE(), INTERVAL 1 WEEK) ";
        sql += "WHEN ?='last-month' THEN DATE_SUB(CURDATE(), INTERVAL 1 MONTH) ";
        sql += "ELSE DATE('1000-01-01') END ";
        sql += "AND code LIKE CASE ";
        sql += "WHEN ?='all' THEN '%%' ";
        sql += "ELSE ? END ";
        sql = SQLBuilder.build(sql, pageable);
        return query(sql, new ArticleMapper(), filter.getDateFormat(), filter.getDateFormat(), filter.getDateFormat(),
                filter.getCategoryCode(), filter.getCategoryCode());
    }

    @Override
    public Long save(Article article) {
        sql = "INSERT INTO article(title, description, thumbnail, content, views, categoryid, ";
        sql += "createddate, createdby, modifieddate, modifiedby) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return insert(sql, article.getTitle(), article.getDescription(), article.getThumbnail(),
                article.getContent(), article.getViews(), article.getCategoryId(), article.getCreatedDate(),
                article.getCreatedBy(), article.getModifiedDate(), article.getModifiedBy());
    }

    @Override
    public void update(Article article) {
        sql = "UPDATE article SET title=?, description=?, thumbnail=?, content=?, views=?, categoryid=?, ";
        sql += "createddate=?, createdby=?, modifieddate=?, modifiedby=? WHERE id=?";
        update(sql, article.getTitle(), article.getDescription(), article.getThumbnail(),
                article.getContent(), article.getViews(), article.getCategoryId(), article.getCreatedDate(),
                article.getCreatedBy(), article.getModifiedDate(), article.getModifiedBy(),
                article.getId());
    }

    @Override
    public void delete(Long id) {
        sql = "DELETE FROM article WHERE id=?";
        update(sql, id);
    }

    @Override
    public Integer getTotalItem() {
        sql = "SELECT count(*) FROM article";
        return count(sql);
    }

}
