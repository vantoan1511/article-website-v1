package com.webtintuc.filter;

import com.webtintuc.constant.SystemConstant;
import com.webtintuc.model.User;
import com.webtintuc.service.IAuthService;
import com.webtintuc.util.SessionUtil;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/admin/*")
public class AuthorizationFilter implements Filter {
    @Inject
    private IAuthService authService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String sessionId = req.getSession().getId();
        User user = authService.getLoggedInUser(sessionId);
        if (user == null || !user.getRoleId().equals(SystemConstant.ADMIN_ROLE)) {
            resp.sendError(405);
        } else {
            chain.doFilter(request, response);
        }
    }
}
