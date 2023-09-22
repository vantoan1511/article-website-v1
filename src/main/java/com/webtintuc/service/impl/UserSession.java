package com.webtintuc.service.impl;

import com.webtintuc.model.User;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

public class UserSession {
    private String id;
    private User user;
    private Instant expiryTime;

    public UserSession(String sessionId) {
        this.id = sessionId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Instant getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(Instant expiryTime) {
        this.expiryTime = expiryTime;
    }
}
