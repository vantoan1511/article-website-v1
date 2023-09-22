package com.webtintuc.api.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webtintuc.model.Category;
import com.webtintuc.model.Model;
import com.webtintuc.service.ICategoryService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/v1/api/admin/category")
public class CategoryAPI extends HttpServlet {

    @Inject
    private ICategoryService categoryService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        String payload = req.getReader().lines().collect(Collectors.joining());
        Category category = mapper.readValue(payload, Category.class);
        category = categoryService.save(category);
        if (category != null) {
            mapper.writeValue(resp.getOutputStream(), category);
        } else {
            mapper.writeValue(resp.getOutputStream(), "Add failed!");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        String payload = req.getReader().lines().collect(Collectors.joining());
        Category category = mapper.readValue(payload, Category.class);
        category = categoryService.update(category);
        if (category != null) {
            mapper.writeValue(resp.getOutputStream(), category);
        } else {
            mapper.writeValue(resp.getOutputStream(), "Update failed!");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        String payload = req.getReader().lines().collect(Collectors.joining());
        Model model = mapper.readValue(payload, Model.class);
        categoryService.delete(model.getIds());
        mapper.writeValue(resp.getOutputStream(), "");
    }
}
