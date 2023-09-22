package com.webtintuc.service.impl;

import com.webtintuc.model.Role;
import com.webtintuc.model.User;
import com.webtintuc.service.IAuthService;
import com.webtintuc.service.IRoleService;
import com.webtintuc.service.IUserService;
import com.webtintuc.util.Cache;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class AuthService implements IAuthService {
    @Inject
    private Cache<String, UserSession> sessionCache;

    @Inject
    private IUserService userService;

    @Inject
    private IRoleService roleService;

    private final Integer sessionTimeout = 2;

    @Override
    public UserSession login(String username, String password, HttpServletRequest req) {
        if (userService.validate(username, password) == null) {
            return null;
        }
        User user = userService.findByUsername(username);
        user.setRole(roleService.findById(user.getRoleId()));
        user.setPassword("hidden");
        UserSession userSession = new UserSession(req.getSession().getId());
        userSession.setUser(user);
        userSession.setExpiryTime(Instant.now().plus(sessionTimeout, ChronoUnit.MINUTES));

        sessionCache.put(userSession.getId(), userSession);

        return userSession;
    }

    @Override
    public void logout(String sessionId) {
        sessionCache.invalidate(sessionId);
    }

    @Override
    public User getLoggedInUser(String sessionId) {
        UserSession userSession = sessionCache.get(sessionId);
        if (userSession == null) {
            return null;
        }
        return userSession.getUser();
    }

    @Override
    public boolean isLoggedIn(String sessionId) {
        return sessionCache.get(sessionId) != null;
    }

    @Override
    public boolean isExpired(String sessionId) {
        UserSession userSession = sessionCache.get(sessionId);
        return Instant.now().isAfter(userSession.getExpiryTime());
    }

    @Override
    public Role getRole(String sessionId) {
        UserSession userSession = sessionCache.get(sessionId);
        if (userSession != null) {
            return userSession.getUser().getRole();
        }
        return null;
    }
}
