package com.webtintuc.controller.web;

import com.webtintuc.service.IUserService;
import com.webtintuc.util.MessageUtil;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/forgot")
public class ForgotController extends HttpServlet {

    @Inject
    private IUserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MessageUtil.showMessage(req);
        req.getRequestDispatcher("/views/web/reset.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        userService.resetPassword(email);
        resp.sendRedirect("/forgot?type=info&msg=email_confirmed");
    }
}
