package com.webtintuc.service;

import com.webtintuc.model.Comment;
import com.webtintuc.sqlbuilder.Pageable;

import java.util.List;

public interface ICommentService {
    List<Comment> findByArticleId(Pageable pageable, Long id);

    List<Comment> findByUserId(Pageable pageable, Long id);

    List<Comment> findByParentId(Pageable pageable, Long id);

    Comment findById(Long id);

    Comment create(Comment comment);

    Comment update(Comment comment);

    void delete(Long[] ids);
}
