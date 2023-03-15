package com.snackman.datnud11.utils;

import java.util.List;
public class CheckRoleUser {
    public String checkRoleUser(List<String> roles, String role){
        return roles.stream().filter(role1 -> role1.equals(role)).findFirst().orElse(null);
    }
}
