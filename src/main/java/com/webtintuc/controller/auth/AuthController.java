package com.webtintuc.controller.auth;

import com.webtintuc.constant.SystemConstant;
import com.webtintuc.model.User;
import com.webtintuc.service.IAuthService;
import com.webtintuc.service.IUserService;
import com.webtintuc.service.impl.UserSession;
import com.webtintuc.util.MessageUtil;
import com.webtintuc.util.ParamMapper;
import com.webtintuc.util.SessionUtil;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/login", "/register", "/logout"})
public class AuthController extends HttpServlet {

    @Inject
    private IAuthService authService;

    @Inject
    private IUserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        String next = req.getParameter("next");
        String location = "";
        if (uri.contains("logout")) {
            authService.logout(req.getSession().getId());
            SessionUtil.remove(req, "session");
            resp.sendRedirect("/login");
        } else {
            if (uri.contains("login")) {
                req.setAttribute("next", next);
                location = "/views/login.jsp";
            } else if (uri.contains("register")) {
                location = "/views/register.jsp";
            }
            MessageUtil.showMessage(req);
            req.getRequestDispatcher(location).forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        String next = req.getParameter("next");
        String uri = req.getRequestURI();
        String location = "/home";
        User user = ParamMapper.toModel(User.class, req);

        if (uri.contains("login")) {
            UserSession session = authService.login(user.getUsername(), user.getPassword(), req);
            if (session != null) {
                SessionUtil.put(req, "session", session);
                if (session.getUser().getRoleId().equals(SystemConstant.ADMIN_ROLE)) {
                    location = "/admin/home";
                } else if (next != null && !next.isEmpty()) {
                    location = next;
                } else {
                    location = "/home";
                }
            } else {
                location = "/login?type=danger&msg=login_failed";
            }
        } else if (uri.contains("register")) {
            user.setAvatar("/template/admin/dist/img/user2-160x160.jpg");
            user.setRoleId(SystemConstant.USER_ROLE);
            user = userService.register(user);
            if (user == null) {
                location = "/register?type=danger&msg=email_username_existed";
            } else {
                location = "/login?type=success&msg=register_success";
            }
        }
        resp.sendRedirect(location);
    }
}
