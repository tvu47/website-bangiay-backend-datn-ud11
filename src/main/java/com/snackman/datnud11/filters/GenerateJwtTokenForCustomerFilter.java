package com.snackman.datnud11.filters;

import com.snackman.datnud11.config.JwtService;
import com.snackman.datnud11.entity.TokenJwt;
import com.snackman.datnud11.exceptions.JwtTokenException;
import com.snackman.datnud11.repo.TokenJwtRepo;
import com.snackman.datnud11.services.TokenService;
import com.snackman.datnud11.services.auth.UserAuth;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class GenerateJwtTokenForCustomerFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private TokenJwtRepo tokenJwtRepo;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        log.info("-----start generate filter-------");
        //refresh token
        String username = request.getParameter("username");
        log.info("username in generate token: {}", username);
        tokenJwtRepo.setIsExpired(username);

        // generate token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication!=null) {
            UserAuth userAuth = (UserAuth) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String jwt = jwtService.generateToken(userAuth);
            log.info("jwt in generate token service: {}", jwt);
            TokenJwt tokenJwt = new TokenJwt();
            tokenJwt.setToken(jwt);
            tokenJwt.setExpire(true);
            tokenJwt.setUsername(userAuth.getUsername());
            try {
                tokenService.saveToken(tokenJwt);
            } catch (JwtTokenException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            request.setAttribute("token", jwt);
        }
        log.info("-----end generate filter-------");

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/api/v1/customers/login");
    }
}
