package com.webtintuc.util;

import javax.servlet.http.HttpServletRequest;

public class SessionUtil {
    public static void put(HttpServletRequest req, String key, Object value) {
        req.getSession().setAttribute(key, value);
    }

    public static void remove(HttpServletRequest req, String key) {
        req.getSession().removeAttribute(key);
    }

    public static Object get(HttpServletRequest req, String key) {
        return req.getSession().getAttribute(key);
    }
}
