package com.webtintuc.controller.web;

import com.webtintuc.model.Model;
import com.webtintuc.service.IArticleService;
import com.webtintuc.service.ICategoryService;
import com.webtintuc.sqlbuilder.Filter;
import com.webtintuc.sqlbuilder.Pageable;
import com.webtintuc.util.ParamMapper;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/categories/*",
        initParams = {@WebInitParam(name = "page", value = "1"),
                @WebInitParam(name = "limit", value = "10")})
public class CategoryController extends HttpServlet {

    @Inject
    private ICategoryService categoryService;

    @Inject
    private IArticleService articleService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        String code = uri.substring(uri.lastIndexOf("/") + 1);
        String location = "";
        Model model = ParamMapper.toModel(Model.class, req);
        if (code.equals("categories") || code.isEmpty()) {
            resp.sendRedirect("/home");
        } else {
            if (model.getPage() == null || model.getLimit() == null) {
                model.setPage(Integer.valueOf(getInitParameter("page")));
                model.setLimit(Integer.valueOf(getInitParameter("limit")));
            }
            model.setCategory(categoryService.findByCode(code));
            model.setArticles(articleService.findByCategoryCode(
                    new Pageable(model.getPage(), model.getLimit()), code));
            model.getArticles().forEach(
                    item -> item.setCategory(categoryService.findById(item.getCategoryId()))
            );
            model.setTotalItems(articleService.countByFilters(new Filter("", "anytime", code)));
            model.setTotalPages((int) Math.ceil((double) model.getTotalItems() / model.getLimit()));
            location = "/views/web/category.jsp";


            req.setAttribute("model", model);
            req.getRequestDispatcher(location).forward(req, resp);
        }

    }
}
