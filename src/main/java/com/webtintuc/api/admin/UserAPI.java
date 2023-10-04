package com.webtintuc.api.admin;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.webtintuc.constant.SystemConstant;
import com.webtintuc.model.Model;
import com.webtintuc.model.User;
import com.webtintuc.service.IUserService;
import com.webtintuc.util.ApiUtils;
import org.mindrot.jbcrypt.BCrypt;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/v1/api/admin/user")
public class UserAPI extends HttpServlet {

    @Inject
    private IUserService userService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ApiUtils.init(req, resp);
        ApiUtils.returnJsonData(resp, userService.register(ApiUtils.parseRequestBody(req, User.class)));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ApiUtils.init(req, resp);

        User updatedUser = ApiUtils.parseRequestBody(req, User.class);
        User oldUser = userService.findById(updatedUser.getId());
        if (updatedUser.getPassword().isEmpty()) {
            updatedUser.setPassword(oldUser.getPassword());
        } else {
            updatedUser.setPassword(BCrypt.hashpw(updatedUser.getPassword(), SystemConstant.SALT));
        }
        updatedUser.setUsername(oldUser.getUsername());
        ApiUtils.returnJsonData(resp, userService.update(updatedUser));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Model model = ApiUtils.parseRequestBody(req, Model.class);
        userService.delete(model.getIds());
        ApiUtils.returnJsonData(resp, "");
    }
}
