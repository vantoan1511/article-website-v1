package com.webtintuc.util;

import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;

public class URIUtils {
    public static <T> T toModel(Class<T> tclass, HttpServletRequest req) {
        T object = null;
        try {
            object = tclass.getDeclaredConstructor().newInstance();
            BeanUtils.populate(object, req.getParameterMap());
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException |
                 InstantiationException e) {
            throw new RuntimeException(e);
        }
        return object;
    }

    public static String getPathParam(HttpServletRequest req) {
        String uri = req.getRequestURI();
        return uri.substring(uri.lastIndexOf("/") + 1);
    }

}
