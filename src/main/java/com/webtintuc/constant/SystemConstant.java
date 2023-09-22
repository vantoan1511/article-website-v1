package com.webtintuc.constant;

import org.mindrot.jbcrypt.BCrypt;

public class SystemConstant {
    public static final Long ADMIN_ROLE = 1L;
    public static final Long USER_ROLE = 2L;
    public static final String SALT = BCrypt.gensalt(12);

}
