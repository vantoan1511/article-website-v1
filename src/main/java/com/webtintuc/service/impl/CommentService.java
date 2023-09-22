package com.webtintuc.service.impl;

import com.webtintuc.dao.ICommentDao;
import com.webtintuc.model.Comment;
import com.webtintuc.service.ICommentService;
import com.webtintuc.sqlbuilder.Pageable;

import javax.inject.Inject;
import java.util.List;

public class CommentService implements ICommentService {

    @Inject
    private ICommentDao commentDao;

    @Override
    public List<Comment> findByArticleId(Pageable pageable, Long id) {
        return commentDao.findByArticleId(pageable, id);
    }

    @Override
    public List<Comment> findByUserId(Pageable pageable, Long id) {
        return commentDao.findByUserId(pageable, id);
    }

    @Override
    public List<Comment> findByParentId(Pageable pageable, Long id) {
        return commentDao.findByParentId(pageable, id);
    }

    @Override
    public Comment findById(Long id) {
        return commentDao.findById(id);
    }
}
