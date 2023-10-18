package com.webtintuc.controller.admin;

import com.webtintuc.model.Model;
import com.webtintuc.service.IArticleService;
import com.webtintuc.service.ICommentService;
import com.webtintuc.service.IRoleService;
import com.webtintuc.service.IUserService;
import com.webtintuc.sqlbuilder.Pageable;
import com.webtintuc.sqlbuilder.Sorter;
import com.webtintuc.util.URIUtils;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/admin/users",
        initParams = {@WebInitParam(name = "page", value = "1"),
                @WebInitParam(name = "limit", value = "5"),
                @WebInitParam(name = "sortBy", value = "username"),
                @WebInitParam(name = "sortOrder", value = "ASC")})
public class UserController extends HttpServlet {
    @Inject
    private IUserService userService;

    @Inject
    private IRoleService roleService;

    @Inject
    private IArticleService articleService;
    @Inject
    private ICommentService commentService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String tab = req.getParameter("tab");
        String location = "";
        Model model = URIUtils.toModel(Model.class, req);

        if (tab == null || tab.equals("list")) {

            if (model.getPage() == null || model.getLimit() == null) {
                model.setPage(Integer.parseInt(getInitParameter("page")));
                model.setLimit(Integer.parseInt(getInitParameter("limit")));
            }
            if (model.getSortBy() == null || model.getSortOrder() == null) {
                model.setSortBy(getInitParameter("sortBy"));
                model.setSortOrder(getInitParameter("sortOrder"));
            }

            model.setTotalItems(userService.getTotalItems());
            model.setTotalPages((int) Math.ceil((double) model.getTotalItems() / model.getLimit()));
            model.setUsers(userService.findAll(new Pageable(model.getPage(), model.getLimit(),
                    new Sorter(model.getSortBy(), model.getSortOrder()))));
            location = "/views/admin/user/list.jsp";
        } else if (tab.equals("edit")) {
            String id = req.getParameter("id");
            if (id != null) {
                model.setUser(userService.findById(Long.valueOf(id)));
                model.getUser().setPassword("");

                req.setAttribute("totalComments",
                        commentService.findByUserId(null, model.getUser().getId()).size());
                req.setAttribute("totalArticles",
                        articleService.findByAuthorName(null, model.getUser().getUsername()).size());
            }
            model.setRoles(roleService.findAll(null));
            location = "/views/admin/user/profile.jsp";
        }
        req.setAttribute("model", model);
        req.getRequestDispatcher(location).forward(req, resp);
    }
}
