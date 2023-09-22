package com.webtintuc.model;

public class User extends BaseModel {
    private String fullname;
    private String email;
    private String username;
    private String password;
    private String avatar;
    private String token;
    private Long roleId;
    private Role role;

    public String getFullname() {
        return fullname;
    }

    public User setFullname(String fullname) {
        this.fullname = fullname;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getAvatar() {
        return avatar;
    }

    public User setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public String getToken() {
        return token;
    }

    public User setToken(String token) {
        this.token = token;
        return this;
    }

    public Long getRoleId() {
        return roleId;
    }

    public User setRoleId(Long roleId) {
        this.roleId = roleId;
        return this;
    }

    public Role getRole() {
        return role;
    }

    public User setRole(Role role) {
        this.role = role;
        return this;
    }
}
