package com.webtintuc.controller.web;

import com.webtintuc.model.Article;
import com.webtintuc.model.Comment;
import com.webtintuc.model.Model;
import com.webtintuc.service.IArticleService;
import com.webtintuc.service.ICategoryService;
import com.webtintuc.service.ICommentService;
import com.webtintuc.service.IUserService;
import com.webtintuc.sqlbuilder.Pageable;
import com.webtintuc.sqlbuilder.Sorter;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/article")
public class ArticleController extends HttpServlet {

    @Inject
    private IArticleService articleService;

    @Inject
    private ICategoryService categoryService;

    @Inject
    private ICommentService commentService;

    @Inject
    private IUserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        Model model = new Model();
        if (id == null) {
            resp.sendError(404);
        } else {
            Article article = articleService.findById(Long.valueOf(id));
            if (article == null) {
                resp.sendError(404);
            } else {
                List<Comment> comments = commentService.findByArticleId(
                        new Pageable(1, 1000,
                                new Sorter("createddate", "DESC")), Long.valueOf(id));

                article.setViews(article.getViews() + 1);
                articleService.update(article);
                article.setCategory(categoryService.findById(article.getCategoryId()));

                comments.forEach(
                        item -> {
                            item.setUser(userService.findById(item.getUserId()));
                            item.setParent(commentService.findById(item.getParentId()));
                        }
                );

                model.setCategories(categoryService.findAll());

                req.setAttribute("article", article);
                req.setAttribute("comments", comments);
                req.setAttribute("model", model);
                req.getRequestDispatcher("/views/web/article.jsp").forward(req, resp);
            }
            //testing section
        }
    }
}
