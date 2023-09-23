package com.webtintuc.dao.impl;

import com.webtintuc.dao.ICommentDao;
import com.webtintuc.mapper.CommentMapper;
import com.webtintuc.model.Comment;
import com.webtintuc.sqlbuilder.Pageable;
import com.webtintuc.sqlbuilder.SQLBuilder;

import java.util.ArrayList;
import java.util.List;

public class CommentDao extends JdbcTemplateImpl<Comment> implements ICommentDao {

    private String sql;
    private List<Comment> comments = new ArrayList<>();

    @Override
    public List<Comment> findByArticleId(Pageable pageable, Long id) {
        sql = "SELECT * FROM comments WHERE articleid=? ";
        sql = SQLBuilder.build(sql, pageable);
        return query(sql, new CommentMapper(), id);
    }

    @Override
    public List<Comment> findByUserId(Pageable pageable, Long id) {
        sql = "SELECT * FROM comments WHERE userid=? ";
        sql = SQLBuilder.build(sql, pageable);
        return query(sql, new CommentMapper(), id);
    }

    @Override
    public List<Comment> findByParentId(Pageable pageable, Long id) {
        sql = "SELECT * FROM comments WHERE parentid=? ";
        sql = SQLBuilder.build(sql, pageable);
        return query(sql, new CommentMapper(), id);
    }

    @Override
    public Comment findById(Long id) {
        sql = "SELECT * FROM comments WHERE id=? ";
        comments = query(sql, new CommentMapper(), id);
        return comments.isEmpty() ? null : comments.get(0);
    }

    @Override
    public Long create(Comment comment) {
        sql = "INSERT INTO comments(content, createddate, modifieddate, userid, articleid, parentid) ";
        sql += "VALUES(?, ?, ?, ?, ?, ?)";
        return insert(sql, comment.getContent(), comment.getCreatedDate(), comment.getModifiedDate(),
                comment.getUserId(), comment.getArticleId(), comment.getParentId());
    }

    @Override
    public void update(Comment comment) {
        sql = "UPDATE comments SET content=?, createddate=?, modifieddate=?, userid=?, articleid=?, parentid=? ";
        sql += "WHERE id=? ";
        update(sql, comment.getContent(), comment.getCreatedDate(), comment.getModifiedDate(),
                comment.getUserId(), comment.getArticleId(), comment.getParentId(), comment.getId());
    }

    @Override
    public void delete(Long id) {
        sql = "DELETE FROM comments WHERE id=? ";
        update(sql, id);
    }
}
