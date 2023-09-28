package com.webtintuc.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.stream.Collectors;

public class ApiUtils {

    public static void init(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        setCharacterEncoding(req);
        setJsonContentType(resp);
    }

    public static <T> T parseRequestBody(HttpServletRequest req, Class<T> tclass) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String payload = req.getReader().lines().collect(Collectors.joining());
            return mapper.readValue(payload, tclass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void returnJsonData(HttpServletResponse resp, Object object) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            if (object != null) {
                mapper.writeValue(resp.getOutputStream(), object);
            } else {
                resp.sendError(500);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setJsonContentType(HttpServletResponse resp) {
        resp.setContentType("application/json");
    }

    public static void setCharacterEncoding(HttpServletRequest req) throws UnsupportedEncodingException {
        req.setCharacterEncoding("UTF-8");
    }
}
