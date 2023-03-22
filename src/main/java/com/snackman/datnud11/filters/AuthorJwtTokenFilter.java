package com.snackman.datnud11.filters;

import com.snackman.datnud11.config.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@RequiredArgsConstructor
@Slf4j
public class AuthorJwtTokenFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        log.info("-----start author filter-------");
        final String authHeader = request.getHeader("Authorization");
        final String jwtFromClient = authHeader.substring(7);
        log.info("jwtfrom Client:  ", jwtFromClient);
        String jwtFromServer  = jwtService.getSessionJwt(null);
        log.info("jwtfrom server:  ", jwtFromServer);
        if (jwtFromServer==null){
            log.error("jwt from server not save in cache, you must get new login");
            log.info("-----end author filter-------");
            filterChain.doFilter(request,response);
        }
        if (jwtFromServer.equals(jwtFromClient)){
            log.info("Author is ok....");
            log.info("-----end author filter-------");
            filterChain.doFilter(request, response);
        }
        log.info("-----end author filter-------");
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().equals("/api/v1/admin/login");
    }
}
