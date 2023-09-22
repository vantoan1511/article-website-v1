package com.webtintuc.mapper;

import com.webtintuc.model.Comment;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommentMapper implements RowMapper<Comment> {
    @Override
    public Comment mapRow(ResultSet set) {
        Comment comment = new Comment();
        try {
            comment.setId(set.getLong("id"));
            comment.setContent(set.getString("content"));
            comment.setCreatedDate(set.getTimestamp("createddate"));
            comment.setModifiedDate(set.getTimestamp("modifieddate"));
            comment.setUserId(set.getLong("userid"));
            comment.setArticleId(set.getLong("articleid"));
            comment.setParentId(set.getLong("parentid"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return comment;
    }
}
