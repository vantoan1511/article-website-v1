package com.webtintuc.mapper;

import com.webtintuc.model.Article;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ArticleMapper implements RowMapper<Article> {
    @Override
    public Article mapRow(ResultSet set) {
        Article article = new Article();
        try {
            article.setId(set.getLong("id"));
            article.setTitle(set.getString("title"));
            article.setDescription(set.getString("description"));
            article.setThumbnail(set.getString("thumbnail"));
            article.setContent(set.getString("content"));
            article.setViews(set.getInt("views"));
            article.setCategoryId(set.getLong("categoryid"));
            article.setCreatedDate(set.getTimestamp("createddate"));
            article.setCreatedBy(set.getString("createdby"));
            article.setModifiedDate(set.getTimestamp("modifieddate"));
            article.setModifiedBy(set.getString("modifiedby"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return article;
    }
}
