package com.webtintuc.converter;

import com.webtintuc.model.User;

import java.lang.reflect.Field;

public class UserConverter {
    public static void convert(User des, User src) {
        Class<?> userClass = User.class;
        Field[] fields = userClass.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(src);
                if (value != null && !"".equals(value)) {
                    field.set(des, value);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
