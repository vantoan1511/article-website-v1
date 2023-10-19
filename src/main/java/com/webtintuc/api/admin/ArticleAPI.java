package com.webtintuc.api.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webtintuc.constant.SystemConstant;
import com.webtintuc.model.Article;
import com.webtintuc.model.Model;
import com.webtintuc.model.User;
import com.webtintuc.service.IArticleService;
import com.webtintuc.service.IAuthService;
import com.webtintuc.util.ApiUtils;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/v1/api/admin/article")
public class ArticleAPI extends HttpServlet {
    @Inject
    private IAuthService authService;

    @Inject
    private IArticleService articleService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");*/
        ApiUtils.init(req, resp);
        /*mapper = new ObjectMapper();
        payload = req.getReader().lines().collect(Collectors.joining());*/
        /*Article article = mapper.readValue(payload, Article.class);
        User author = authService.getLoggedInUser(req.getSession().getId());
        if (author == null || !author.getRoleId().equals(SystemConstant.ADMIN_ROLE)) {
            resp.sendError(403);
        } else {
            article.setViews(0);
            article.setCreatedDate(Timestamp.from(Instant.now()));
            article.setModifiedDate(article.getCreatedDate());
            article.setCreatedBy(author.getUsername());
            article.setModifiedBy(article.getCreatedBy());
            mapper.writeValue(resp.getOutputStream(), articleService.save(article));
        }*/
        User author = authService.getLoggedInUser(req.getSession().getId());
        Article article = ApiUtils.parseRequestBody(req, Article.class);
        article.setCreatedBy(author.getUsername());
        article.setModifiedBy(article.getCreatedBy());
        ApiUtils.returnJsonData(resp, articleService.create(article));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        mapper = new ObjectMapper();
        payload = req.getReader().lines().collect(Collectors.joining());
        Article article = mapper.readValue(payload, Article.class);
        User author = authService.getLoggedInUser(req.getSession().getId());
        if (author == null || !author.getRoleId().equals(SystemConstant.ADMIN_ROLE)) {
            resp.sendError(403);
        } else {
            Article oldArticle = articleService.findById(article.getId());
            if (oldArticle != null) {
                article.setViews(oldArticle.getViews());
                article.setCreatedDate(oldArticle.getCreatedDate());
                article.setModifiedDate(Timestamp.from(Instant.now()));
                article.setCreatedBy(oldArticle.getCreatedBy());
                article.setModifiedBy(author.getUsername());
                mapper.writeValue(resp.getOutputStream(), articleService.update(article));
            } else {
                mapper.writeValue(resp.getOutputStream(), "Article does not existed!");
            }
        }*/
        ApiUtils.init(req, resp);
        User author = authService.getLoggedInUser(req.getSession().getId());
        Article article = ApiUtils.parseRequestBody(req, Article.class);
        Article oldArticle = articleService.findById(article.getId());
        article.setCreatedBy(oldArticle.getCreatedBy());
        article.setCreatedDate(oldArticle.getCreatedDate());
        article.setModifiedDate(Timestamp.from(Instant.now()));
        article.setModifiedBy(author.getUsername());
        article.setViews(articleService.findById(article.getId()).getViews());
        ApiUtils.returnJsonData(resp, articleService.update(article));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*resp.setContentType("application/json");
        mapper = new ObjectMapper();
        payload = req.getReader().lines().collect(Collectors.joining());
        Model model = mapper.readValue(payload, Model.class);
        if (model.getIds() != null) {
            articleService.delete(model.getIds());
            mapper.writeValue(resp.getOutputStream(), "Deleted");
        }*/
        ApiUtils.init(req, resp);
        Model model = ApiUtils.parseRequestBody(req, Model.class);
        articleService.delete(model.getIds());
        ApiUtils.returnJsonData(resp, "");
    }
}
