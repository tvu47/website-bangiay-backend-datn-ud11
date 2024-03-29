package com.snackman.datnud11.filters;

import com.snackman.datnud11.exceptions.UserNotfoundException;
import com.snackman.datnud11.services.auth.UserAuth;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.jaas.AuthorityGranter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static java.util.Arrays.stream;

@Component
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private UserDetailsService userDetailsService;
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailsService){
        this.userDetailsService = userDetailsService;
        super.setRequiresAuthenticationRequestMatcher(new OrRequestMatcher(
                new AntPathRequestMatcher("/api/v1/admin/login", "POST"),
                new AntPathRequestMatcher("/api/v1/customers/login", "POST")));
        this.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        log.info("-----start JwtAuthentication1Filter -----");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
            UserAuth userAuth= (UserAuth) userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken;
            if (request.getServletPath().equals("/api/v1/admin/login")){
                log.info("----role admin---"+userAuth.getAuthorities());

//                Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//                authorities.add(new SimpleGrantedAuthority("ADMIN_ROLE"));
                authenticationToken = new UsernamePasswordAuthenticationToken(username, password, userAuth.getAuthorities());
            } else {
                Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("CLIENT_ROLE"));

                authenticationToken = new UsernamePasswordAuthenticationToken(username, password, authorities);
            }

        log.info("-----end JwtAuthentication1Filter -----");
        return this.getAuthenticationManager().authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        log.info("authentication successfully !!!");
        log.info("User_details is setting ....");
        String username = request.getParameter("username");
        UserAuth userAuth= (UserAuth) userDetailsService.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userAuth,
                null,
                userAuth.getAuthorities()
        );
        usernamePasswordAuthenticationToken.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        chain.doFilter(request,response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        log.info("authentication false !!!");
        throw new RuntimeException("username or password is wrong.");
    }

    @Override
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }
}
