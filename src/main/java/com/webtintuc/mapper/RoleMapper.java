package com.webtintuc.mapper;

import com.webtintuc.model.Role;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleMapper implements RowMapper<Role> {
    @Override
    public Role mapRow(ResultSet set) {
        Role role = new Role();
        try {
            role.setId(set.getLong("id"));
            role.setCode(set.getString("code"));
            role.setName(set.getString("name"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return role;
    }
}
