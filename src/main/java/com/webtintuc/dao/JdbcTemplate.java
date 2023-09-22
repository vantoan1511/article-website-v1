package com.webtintuc.dao;

import com.webtintuc.mapper.RowMapper;

import java.util.List;

public interface JdbcTemplate<T> {

    List<T> query(String sql, RowMapper<T> mapper, Object... parameters);

    Long insert(String sql, Object... parameters);

    void update(String sql, Object... parameters);

    Integer count(String sql, Object... parameters);
}
