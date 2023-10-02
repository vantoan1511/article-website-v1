package com.webtintuc.controller.web;

import com.webtintuc.constant.SystemConstant;
import com.webtintuc.model.User;
import com.webtintuc.service.IUserService;
import org.mindrot.jbcrypt.BCrypt;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/reset"})
public class ResetController extends HttpServlet {

    @Inject
    private IUserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");
        User user = userService.findByToken(token);
        if (token != null && !token.equals("none")) {
            if (user != null) {
                req.getSession().setAttribute("token", token);
                resp.sendRedirect("/reset");
            } else {
                resp.sendError(404);
            }
        } else {
            if (req.getSession().getAttribute("token") != null) {
                req.getRequestDispatcher("/views/web/change-password.jsp").forward(req, resp);
            } else {
                resp.sendError(404);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String password = req.getParameter("password");
        String token = req.getSession().getAttribute("token").toString();
        User user = userService.findByToken(token);
        user.setToken("none");
        user.setPassword(password);
        user = userService.update(user);
        req.getSession().removeAttribute("token");
        resp.sendRedirect("/login?type=success&msg=reset_success");
    }
}
