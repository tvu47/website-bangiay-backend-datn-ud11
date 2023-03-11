package com.snackman.datnud11.services;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserServices {
    UserDetails getUserDetailFromDB(String username);
    List<String> getRoleListByUsername(String username);
}
