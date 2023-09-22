package com.webtintuc.dao;

import com.webtintuc.model.Comment;
import com.webtintuc.sqlbuilder.Pageable;

import java.util.List;

public interface ICommentDao {

    List<Comment> findByArticleId(Pageable pageable, Long id);

    List<Comment> findByUserId(Pageable pageable, Long id);

    List<Comment> findByParentId(Pageable pageable, Long id);

    Comment findById(Long id);

    Long create(Comment comment);

    void update(Comment comment);

}
