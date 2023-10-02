package com.webtintuc.controller.web;

import com.webtintuc.model.User;
import com.webtintuc.service.IUserService;
import com.webtintuc.util.URIUtils;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/profile/*")
public class ProfileController extends HttpServlet {

    @Inject
    private IUserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = URIUtils.getPathParam(req);
        String location = "";
        if (username.isEmpty() || username.equals("profile")) {
            resp.sendError(404);
        } else {
            User user = userService.findByUsername(username);
            location = "/views/web/profile.jsp";

            req.setAttribute("user", user);
            req.getRequestDispatcher(location).forward(req, resp);
        }
    }
}
