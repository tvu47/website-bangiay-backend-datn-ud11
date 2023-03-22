package com.snackman.datnud11.filters;

import com.snackman.datnud11.config.JwtService;
import com.snackman.datnud11.services.auth.UserAuth;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@Slf4j
public class GenerateJwtTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        log.info("-----start generate filter-------");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication!=null){
            UserAuth userAuth = (UserAuth) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String jwt = jwtService.generateToken(userAuth);
            log.info("jwt in generate token service: ", jwt);
            String jwtCache= jwtService.getSessionJwt(jwt);
            log.info("jwtCache in generate token service: ", jwtCache);
        }
        log.info("-----end generate filter-------");
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/api/v1/admin/login");
    }
}
