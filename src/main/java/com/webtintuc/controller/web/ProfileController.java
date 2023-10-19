package com.webtintuc.controller.web;

import com.webtintuc.model.Comment;
import com.webtintuc.model.User;
import com.webtintuc.service.IArticleService;
import com.webtintuc.service.ICommentService;
import com.webtintuc.service.IUserService;
import com.webtintuc.sqlbuilder.Pageable;
import com.webtintuc.sqlbuilder.Sorter;
import com.webtintuc.util.URIUtils;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/profile/*")
public class ProfileController extends HttpServlet {

    @Inject
    private IUserService userService;

    @Inject
    private ICommentService commentService;

    @Inject
    private IArticleService articleService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = URIUtils.getPathParam(req);
        String location = "";
        User user = userService.findByUsername(username);
        user.setPassword("");
        if (username.isEmpty() || username.equals("profile") || user == null) {
            location = "/views/web/404.jsp";
        } else {
            List<Comment> comments = commentService.findByUserId(
                    new Pageable(1, 1000,
                            new Sorter("createddate", "DESC")), user.getId());
            comments.forEach(
                    item -> item.setArticle(articleService.findById(item.getArticleId()))
            );
            location = "/views/web/profile.jsp";

            req.setAttribute("user", user);
            req.setAttribute("comments", comments);
            req.setAttribute("articles", articleService.findByAuthorName(null, user.getUsername()));
        }
        req.getRequestDispatcher(location).forward(req, resp);
    }
}
