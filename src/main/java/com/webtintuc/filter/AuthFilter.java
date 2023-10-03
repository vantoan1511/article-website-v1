package com.webtintuc.filter;

import com.webtintuc.service.IAuthService;
import com.webtintuc.util.SessionUtil;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/login", "/register", "/logout", "/settings"})
public class AuthFilter implements Filter {

    @Inject
    private IAuthService authService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        String sessionId = req.getSession().getId();
        if (authService.isLoggedIn(sessionId) &&
                (uri.contains("login") || uri.contains("register"))) {
            resp.sendRedirect("/home");
        } else if (uri.contains("settings") && !authService.isLoggedIn(sessionId)) {
            resp.sendRedirect("/login?type=danger&msg=login_required");
        } else {
            chain.doFilter(request, response);
        }
    }
}
