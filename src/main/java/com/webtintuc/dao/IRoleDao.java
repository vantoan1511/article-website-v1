package com.webtintuc.dao;

import com.webtintuc.model.Role;
import com.webtintuc.sqlbuilder.Pageable;

import java.util.List;

public interface IRoleDao {

    List<Role> findAll(Pageable pageable);

    Role findById(Long id);

    Role findByCode(String code);
}
