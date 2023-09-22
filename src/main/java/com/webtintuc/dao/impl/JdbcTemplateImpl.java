package com.webtintuc.dao.impl;

import com.webtintuc.dao.JdbcTemplate;
import com.webtintuc.mapper.RowMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class JdbcTemplateImpl<T> implements JdbcTemplate<T> {

    ResourceBundle db = ResourceBundle.getBundle("db");

    private Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = db.getString("url");
            String username = db.getString("username");
            String password = db.getString("password");
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public List<T> query(String sql, RowMapper<T> mapper, Object... parameters) {
        List<T> results = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            setParameters(statement, parameters);
            rs = statement.executeQuery();
            while (rs.next()) {
                results.add(mapper.mapRow(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return results;
    }

    @Override
    public Long insert(String sql, Object... parameters) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        Long id = -1L;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            setParameters(statement, parameters);
            statement.executeUpdate();
            rs = statement.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getLong(1);
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return id;
    }

    @Override
    public void update(String sql, Object... parameters) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(sql);
            setParameters(statement, parameters);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private void setParameters(PreparedStatement statement, Object[] parameters) {
        try {
            for (int i = 0; i < parameters.length; i++) {
                if (parameters[i] == null) {
                    statement.setNull(i + 1, Types.VARCHAR);
                } else {
                    switch (parameters[i].getClass().getName()) {
                        case "java.lang.Long", "java.lang.String", "java.lang.Integer",
                                "java.lang.Boolean", "java.sql.Timestamp" -> statement.setObject(i + 1, parameters[i]);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Integer count(String sql, Object... parameters) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        Integer count = 0;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            setParameters(statement, parameters);
            rs = statement.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return count;
    }
}
