package com.webtintuc.dao.impl;

import com.webtintuc.dao.IRoleDao;
import com.webtintuc.mapper.RoleMapper;
import com.webtintuc.model.Role;
import com.webtintuc.sqlbuilder.Pageable;
import com.webtintuc.sqlbuilder.SQLBuilder;

import java.util.ArrayList;
import java.util.List;

public class RoleDao extends JdbcTemplateImpl<Role> implements IRoleDao {
    String sql;
    List<Role> roles = new ArrayList<>();

    @Override
    public List<Role> findAll(Pageable pageable) {
        sql = "SELECT * FROM role ";
        sql = SQLBuilder.build(sql, pageable);
        return query(sql, new RoleMapper());
    }

    @Override
    public Role findById(Long id) {
        sql = "SELECT * FROM role WHERE id=?";
        roles = query(sql, new RoleMapper(), id);
        return roles.isEmpty() ? new Role() : roles.get(0);
    }

    @Override
    public Role findByCode(String code) {
        sql = "SELECT * FROM role WHERE code=?";
        roles = query(sql, new RoleMapper(), code);
        return roles.isEmpty() ? new Role() : roles.get(0);
    }
}
