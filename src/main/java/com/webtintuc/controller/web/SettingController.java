package com.webtintuc.controller.web;

import com.webtintuc.model.User;
import com.webtintuc.service.IAuthService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/settings")
public class SettingController extends HttpServlet {

    @Inject
    private IAuthService authService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String location = "";
        User user = authService.getLoggedInUser(req.getSession().getId());
        System.out.println(user.getPassword());
        location = "/views/web/settings.jsp";
        req.setAttribute("user", user);
        req.getRequestDispatcher(location).forward(req, resp);
    }
}
