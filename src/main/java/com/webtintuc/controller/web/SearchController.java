package com.webtintuc.controller.web;

import com.webtintuc.model.Model;
import com.webtintuc.service.IArticleService;
import com.webtintuc.service.ICategoryService;
import com.webtintuc.sqlbuilder.Filter;
import com.webtintuc.sqlbuilder.Pageable;
import com.webtintuc.sqlbuilder.Sorter;
import com.webtintuc.util.ParamMapper;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/search", initParams = {
        @WebInitParam(name = "page", value = "1"),
        @WebInitParam(name = "limit", value = "10"),
        @WebInitParam(name = "sortBy", value = "title"),
        @WebInitParam(name = "sortOrder", value = "ASC"),
        @WebInitParam(name = "dateFormat", value = "anytime"),
        @WebInitParam(name = "categoryCode", value = "all")
})
public class SearchController extends HttpServlet {

    @Inject
    private ICategoryService categoryService;

    @Inject
    private IArticleService articleService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String location = "/views/web/search.jsp";
        Model model = new Model();
        model = ParamMapper.toModel(Model.class, req);
        if (model.getPage() == null || model.getLimit() == null) {
            model.setPage(Integer.parseInt(getInitParameter("page")));
            model.setLimit(Integer.parseInt(getInitParameter("limit")));
        }
        if (model.getSortBy() == null || model.getSortOrder() == null) {
            model.setSortBy(getInitParameter("sortBy"));
            model.setSortOrder(getInitParameter("sortOrder"));
        }
        if (model.getDateFormat() == null) {
            model.setDateFormat(getInitParameter("dateFormat"));
        }
        if (model.getCategoryCode() == null) {
            model.setCategoryCode(getInitParameter("categoryCode"));
        }
        model.setArticles(articleService.searchResults(
                        new Pageable(model.getPage(), model.getLimit(),
                                new Sorter(model.getSortBy(), model.getSortOrder()))
                        , model.getKeyword(), model.getDateFormat(), model.getCategoryCode()
                )
        );
        model.getArticles().forEach(
                item -> item.setCategory(categoryService.findById(item.getCategoryId()))
        );
        model.setCategories(categoryService.findAll());
        model.setTotalItems(articleService.countByFilters(
                new Filter(model.getKeyword(), model.getDateFormat(), model.getCategoryCode())));
        model.setTotalPages((int) Math.ceil((double) model.getTotalItems() / model.getLimit()));

        req.setAttribute("model", model);
        req.getRequestDispatcher(location).forward(req, resp);

        //testing section
        System.out.println(model.getTotalItems());
    }
}
