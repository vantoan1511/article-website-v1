package com.webtintuc.mapper;

import java.sql.ResultSet;

public interface RowMapper<T> {
    T mapRow(ResultSet set);
}
