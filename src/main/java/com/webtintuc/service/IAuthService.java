package com.webtintuc.service;

import com.webtintuc.model.Role;
import com.webtintuc.model.User;
import com.webtintuc.service.impl.UserSession;

import javax.servlet.http.HttpServletRequest;

public interface IAuthService {
    UserSession login(String username, String password, HttpServletRequest req);

    void logout(String sessionId);

    User getLoggedInUser(String sessionId);

    boolean isLoggedIn(String sessionId);

    boolean isExpired(String sessionId);

    Role getRole(String sessionId);
}
