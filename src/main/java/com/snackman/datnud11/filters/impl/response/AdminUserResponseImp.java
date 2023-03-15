package com.snackman.datnud11.filters.impl.response;

import com.snackman.datnud11.config.JwtService;
import com.snackman.datnud11.consts.FilterTypeConstant;
import com.snackman.datnud11.filters.impl.AbstractUserResponseFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminUserResponseImp extends AbstractUserResponseFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    public AbstractUserResponseFilter get(String userType) {
        return userType.equals(FilterTypeConstant.CONTENT_TYPE_CLIENT_USER) ? this:null;
    }
    @Override
    public void run(HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        jwt = authHeader.substring(7);
        username = jwtService.extractUsername(jwt);
        log.info("username extract: {}", username);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
    }
}
