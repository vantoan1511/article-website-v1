package com.webtintuc.service.impl;

import com.webtintuc.dao.IRoleDao;
import com.webtintuc.model.Role;
import com.webtintuc.service.IRoleService;

import javax.inject.Inject;

public class RoleService implements IRoleService {
    @Inject
    private IRoleDao roleDao;

    @Override
    public Role findById(Long id) {
        return roleDao.findById(id);
    }
}
