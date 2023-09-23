package com.webtintuc.api.web;

import com.webtintuc.model.Comment;
import com.webtintuc.model.Model;
import com.webtintuc.service.ICommentService;
import com.webtintuc.util.ApiUtils;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/v1/api/comments/")
public class CommentAPI extends HttpServlet {

    @Inject
    private ICommentService commentService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ApiUtils.init(req, resp);
        ApiUtils.returnJsonData(resp, commentService.create(ApiUtils.parseRequestBody(req, Comment.class)));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ApiUtils.init(req, resp);
        ApiUtils.returnJsonData(resp, commentService.update(ApiUtils.parseRequestBody(req, Comment.class)));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        commentService.delete(ApiUtils.parseRequestBody(req, Model.class).getIds());
        ApiUtils.returnJsonData(resp, "");
    }
}
