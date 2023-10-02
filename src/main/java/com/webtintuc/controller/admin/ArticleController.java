package com.webtintuc.controller.admin;

import com.webtintuc.model.Model;
import com.webtintuc.sqlbuilder.Pageable;
import com.webtintuc.sqlbuilder.Sorter;
import com.webtintuc.service.IArticleService;
import com.webtintuc.service.ICategoryService;
import com.webtintuc.util.URIUtils;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/admin/article",
        initParams = {@WebInitParam(name = "page", value = "1"),
                @WebInitParam(name = "limit", value = "5"),
                @WebInitParam(name = "sortBy", value = "createddate"),
                @WebInitParam(name = "sortOrder", value = "DESC")})
public class ArticleController extends HttpServlet {

    @Inject
    private IArticleService articleService;

    @Inject
    private ICategoryService categoryService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String tab = req.getParameter("tab");
        String location = "";
        Model model = URIUtils.toModel(Model.class, req);

        if (tab == null || tab.equals("list")) {
            if (model.getPage() == null || model.getLimit() == null) {
                model.setPage(Integer.parseInt(getInitParameter("page")));
                model.setLimit(Integer.parseInt(getInitParameter("limit")));
            }
            if (model.getSortBy() == null || model.getSortOrder() == null) {
                model.setSortBy(getInitParameter("sortBy"));
                model.setSortOrder(getInitParameter("sortOrder"));
            }

            model.setTotalItems(articleService.getTotalItems());
            model.setTotalPages((int) Math.ceil((double) model.getTotalItems() / model.getLimit()));
            model.setArticles(articleService.findAll(new Pageable(model.getPage(), model.getLimit(),
                    new Sorter(model.getSortBy(), model.getSortOrder()))));
            model.getArticles().forEach(
                    item -> item.setCategory(categoryService.findById(item.getCategoryId()))
            );
            location = "/views/admin/article/list.jsp";
        } else if (tab.equals("edit")) {
            String id = req.getParameter("id");
            if (id != null) {
                model.setArticle(articleService.findById(Long.valueOf(id)));
                model.getArticle().setCategory(categoryService.findById(model.getArticle().getCategoryId()));
            }
            model.setCategories(categoryService.findAll());
            location = "/views/admin/article/edit.jsp";
        }
        req.setAttribute("model", model);
        req.getRequestDispatcher(location).forward(req, resp);
    }
}
