package com.mobest1an.LAB4_WEB.model;

public enum Permission {

    USERS_READ("users:read"),
    USERS_ADD_DOTES("users:add_dotes");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
