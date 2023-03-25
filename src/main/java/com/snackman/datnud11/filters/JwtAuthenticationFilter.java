package com.snackman.datnud11.filters;

import com.snackman.datnud11.config.JwtService;
import com.snackman.datnud11.controller.auth.UserAuthenticationService;
import com.snackman.datnud11.entity.TokenJwt;
import com.snackman.datnud11.exceptions.BadRequestException;
import com.snackman.datnud11.exceptions.JwtTokenException;
import com.snackman.datnud11.exceptions.RoleNotFoundException;
import com.snackman.datnud11.exceptions.UserNotfoundException;
import com.snackman.datnud11.services.TokenService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Arrays;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  @Autowired
  private JwtService jwtService;
  @Autowired
  private UserDetailsService userDetailsService;
  @Autowired
  private TokenService tokenService;
  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain
  ) throws ServletException, IOException {
    final String authHeader = request.getHeader("Authorization");
    final String authToken;
    final String username;
    log.info("uri called: {}", request.getRequestURI());
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }
      authToken = authHeader.substring(7);
    try {
      username = jwtService.extractUsername(authToken);
    }catch (ExpiredJwtException e){
      tokenService.hasExpiredTime();
      log.error("Token has expired....");
      throw e;
    }


      if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
        try {
          TokenJwt tokenFromServer = tokenService.getTokenByUsername(username);

          System.out.println("token from server: "+tokenFromServer.getToken());
          System.out.println("token from client: "+authToken);
          if (tokenFromServer.getToken() != authToken){
            throw new BadRequestException("Request is bad. try login again...");
          }
          if (jwtService.isTokenValid(authToken, userDetails)) {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );
            usernamePasswordAuthenticationToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
          }
        } catch (BadRequestException e) {
          throw new RuntimeException(e);
        }
      }
    filterChain.doFilter(request, response);
  }
}
