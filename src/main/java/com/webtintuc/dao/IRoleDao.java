package com.webtintuc.dao;

import com.webtintuc.model.Role;

public interface IRoleDao {
    Role findById(Long id);

    Role findByCode(String code);
}
