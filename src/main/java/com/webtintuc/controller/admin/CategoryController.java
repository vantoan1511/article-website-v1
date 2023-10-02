package com.webtintuc.controller.admin;

import com.webtintuc.model.Category;
import com.webtintuc.service.ICategoryService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/admin/category")
public class CategoryController extends HttpServlet {

    @Inject
    private ICategoryService categoryService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String location = "";
        Category category = new Category();

        if (id != null) {
            category = categoryService.findById(Long.valueOf(id));
        } else {
        }

        location = "/views/admin/category/list.jsp";
        req.setAttribute("category", category);
        req.setAttribute("categories", categoryService.findAll());
        req.getRequestDispatcher(location).forward(req, resp);
    }
}
