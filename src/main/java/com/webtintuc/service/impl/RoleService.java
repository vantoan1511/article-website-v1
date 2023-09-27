package com.webtintuc.service.impl;

import com.webtintuc.dao.IRoleDao;
import com.webtintuc.model.Role;
import com.webtintuc.service.IRoleService;
import com.webtintuc.sqlbuilder.Pageable;

import javax.inject.Inject;
import java.util.List;

public class RoleService implements IRoleService {
    @Inject
    private IRoleDao roleDao;

    @Override
    public List<Role> findAll(Pageable pageable) {
        return roleDao.findAll(pageable);
    }

    @Override
    public Role findById(Long id) {
        return roleDao.findById(id);
    }
}
