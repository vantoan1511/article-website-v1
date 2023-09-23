package com.webtintuc.api.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webtintuc.model.Comment;
import com.webtintuc.model.Model;
import com.webtintuc.service.ICommentService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/v1/api/comments/")
public class CommentAPI extends HttpServlet {

    @Inject
    private ICommentService commentService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        String payload = req.getReader().lines().collect(Collectors.joining());
        Comment comment = new Comment();
        comment = mapper.readValue(payload, Comment.class);
        comment = commentService.create(comment);
        mapper.writeValue(resp.getOutputStream(), comment);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        String payload = req.getReader().lines().collect(Collectors.joining());
        Comment comment = new Comment();
        comment = mapper.readValue(payload, Comment.class);
        comment = commentService.update(comment);
        mapper.writeValue(resp.getOutputStream(), comment);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        String payload = req.getReader().lines().collect(Collectors.joining());
        Model model = mapper.readValue(payload, Model.class);
        commentService.delete(model.getIds());
        mapper.writeValue(resp.getOutputStream(), "");
    }
}
