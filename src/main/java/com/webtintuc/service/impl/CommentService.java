package com.webtintuc.service.impl;

import com.webtintuc.dao.ICommentDao;
import com.webtintuc.model.Comment;
import com.webtintuc.service.ICommentService;
import com.webtintuc.sqlbuilder.Pageable;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.time.Instant;
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

    @Override
    public Comment create(Comment comment) {
        comment.setCreatedDate(Timestamp.from(Instant.now()));
        comment.setModifiedDate(comment.getCreatedDate());
        return commentDao.findById(commentDao.create(comment));
    }

    @Override
    public Comment update(Comment comment) {
        Comment oldComment = commentDao.findById(comment.getId());
        oldComment.setContent(comment.getContent());
        oldComment.setModifiedDate(Timestamp.from(Instant.now()));
        commentDao.update(oldComment);
        return commentDao.findById(comment.getId());
    }

    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            commentDao.delete(id);
        }
    }
}
