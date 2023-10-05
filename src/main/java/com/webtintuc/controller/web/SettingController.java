package com.webtintuc.controller.web;

import com.webtintuc.constant.SystemConstant;
import com.webtintuc.model.User;
import com.webtintuc.service.IAuthService;
import com.webtintuc.service.IUserService;
import com.webtintuc.util.MessageUtil;
import com.webtintuc.util.URIUtils;
import org.mindrot.jbcrypt.BCrypt;

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

    @Inject
    private IUserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String location = "";
        User user = authService.getLoggedInUser(req.getSession().getId());
        location = "/views/web/settings.jsp";
        MessageUtil.showMessage(req);
        req.setAttribute("user", user);
        req.getRequestDispatcher(location).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String location = "/settings";
        User user = URIUtils.toModel(User.class, req);
        User oldUser = userService.findById(user.getId());
        user.setToken(oldUser.getToken());
        user.setRoleId(oldUser.getRoleId());
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            user.setPassword(oldUser.getPassword());
        } else {
            user.setPassword(BCrypt.hashpw(user.getPassword(), SystemConstant.SALT));
        }
        user = userService.update(user);
        location = "/settings?type=success&msg=profile_updated";
        resp.sendRedirect(location);
    }
}
