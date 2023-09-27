package com.webtintuc.service;

import com.webtintuc.model.Role;
import com.webtintuc.sqlbuilder.Pageable;

import java.util.List;

public interface IRoleService {

    List<Role> findAll(Pageable pageable);
    Role findById(Long id);
}
