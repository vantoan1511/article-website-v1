package com.webtintuc.sqlbuilder;

public class SQLBuilder {

    public static String build(String baseSql, Pageable pageable) {
        String sql = baseSql;
        if (pageable != null) {
            if (pageable.getSorter() != null) {
                sql += "ORDER BY " + pageable.getSorter().getSortBy() + " "
                        + pageable.getSorter().getSortOrder() + " ";
            }
            if (pageable.getOffset() != null && pageable.getLimit() != null) {
                sql += "LIMIT " + pageable.getOffset() + ", " + pageable.getLimit();
            }
        }
        return sql;
    }
}
