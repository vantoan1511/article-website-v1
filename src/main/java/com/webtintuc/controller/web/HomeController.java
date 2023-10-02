package com.webtintuc.controller.web;

import com.webtintuc.model.Article;
import com.webtintuc.model.Model;
import com.webtintuc.sqlbuilder.Pageable;
import com.webtintuc.sqlbuilder.Sorter;
import com.webtintuc.service.IArticleService;
import com.webtintuc.service.ICategoryService;
import com.webtintuc.util.ParamMapper;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/home",
        initParams = {@WebInitParam(name = "page", value = "1"),
                @WebInitParam(name = "limit", value = "2"),
                @WebInitParam(name = "sortBy", value = "createddate"),
                @WebInitParam(name = "sortOrder", value = "DESC")})
public class HomeController extends HttpServlet {

    @Inject
    private IArticleService articleService;

    @Inject
    private ICategoryService categoryService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Article> latestArticles = new ArrayList<>();
        List<Article> popularArticles = new ArrayList<>();
        Model model = ParamMapper.toModel(Model.class, req);

        if (model.getPage() == null || model.getLimit() == null) {
            model.setPage(Integer.valueOf(getInitParameter("page")));
            model.setLimit(Integer.parseInt(getInitParameter("limit")));
        }
        if (model.getSortBy() == null || model.getSortOrder() == null) {
            model.setSortBy(getInitParameter("sortBy"));
            model.setSortOrder(getInitParameter("sortOrder"));
        }
        latestArticles = articleService.findAll(new Pageable(1, 10,
                new Sorter("createddate", "desc")));
        latestArticles.forEach(
                item -> item.setCategory(categoryService.findById(item.getCategoryId()))
        );
        popularArticles = articleService.findAll(new Pageable(1, 10,
                new Sorter("views", "desc")));
        popularArticles.forEach(
                item -> item.setCategory(categoryService.findById(item.getCategoryId()))
        );
        model.setTotalItems(articleService.getTotalItems());
        model.setTotalPages((int) Math.ceil((double) model.getTotalItems() / model.getLimit()));
        model.setArticles(articleService.findAll(new Pageable(model.getPage(), model.getLimit(),
                new Sorter(model.getSortBy(), model.getSortOrder()))));
        model.getArticles().forEach(
                item -> item.setCategory(categoryService.findById(item.getCategoryId()))
        );
        model.setCategories(categoryService.findAll());
        req.setAttribute("latest", latestArticles);
        req.setAttribute("popular", popularArticles);
        req.setAttribute("model", model);
        req.getRequestDispatcher("/views/web/home.jsp").forward(req, resp);

        //testing section
        /*List<Article> articles = articleService.findFromLastMonth(new Pageable(1, 10));
        articles.forEach(item -> System.out.println(item.getId()));*/
    }
}
