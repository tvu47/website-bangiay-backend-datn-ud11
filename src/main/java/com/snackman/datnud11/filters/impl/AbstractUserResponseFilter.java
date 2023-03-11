package com.snackman.datnud11.filters.impl;

import jakarta.servlet.http.HttpServletRequest;

public abstract class AbstractUserResponseFilter {
    public abstract AbstractUserResponseFilter get(String userType);
    public abstract void run(HttpServletRequest request);
}
