package com.snackman.datnud11.filters;

import com.snackman.datnud11.repo.TokenJwtRepo;
import com.snackman.datnud11.services.imp.UserServiceImp;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LogoutForAdminFilter implements LogoutHandler {

    private final UserServiceImp userServiceImp;

    public LogoutForAdminFilter(UserServiceImp userServiceImp){
        this.userServiceImp = userServiceImp;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        //refresh token
        log.info("---- refresh token ------");
        String username = request.getParameter("username");
        userServiceImp.refreshToken(username);
    }
}
