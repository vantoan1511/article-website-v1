package com.webtintuc.api.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webtintuc.model.Article;
import com.webtintuc.service.IArticleService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/api/v1/article")
public class ArticleAPI extends HttpServlet {

    @Inject
    private IArticleService articleService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        ObjectMapper mapper = new ObjectMapper();
        List<Article> articles = new ArrayList<>();
        if (id == null) {
            articles = articleService.findAll();
        }
        mapper.writeValue(resp.getOutputStream(), articles);
    }
}
