package com.snackman.datnud11.filters;

import com.snackman.datnud11.config.JwtService;
import com.snackman.datnud11.entity.TokenJwt;
import com.snackman.datnud11.repo.TokenJwtRepo;
import com.snackman.datnud11.services.TokenService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@RequiredArgsConstructor
@Slf4j
public class ValidateJwtTokenFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final TokenService tokenService;
    private final TokenJwtRepo tokenJwtRepo;
    private final UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;
        log.info("-----start validate filter-------");
        // check header have token or not
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            log.info("authen is require...");
            filterChain.doFilter(request, response);
            return;
        }

        jwt= authHeader.substring(7);
        // extract username by token of client sent up
        try {
            username = jwtService.extractUsername(jwt);
        }catch (ExpiredJwtException e){
            log.error("Token has expired....");
            throw e;
        }
        log.error("username extract :{}", username);
        // username is define
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        TokenJwt tokenJwt = tokenService.getTokenByUsername(username);

        log.info("token client : {}", jwt);
        log.info("token database : {}", tokenJwt);
        // check 2 token is the same or not
        if (!tokenJwt.getToken().equals(jwt)){
            throw new BadCredentialsException("token is shit");
        }

        //validate token from database and client sent up
        if (null!=tokenJwt || null!=jwt){
            try {
                jwtService.validateAccessToken(tokenJwt.getToken());
                jwtService.validateAccessToken(jwt);
            }catch (Exception e){
                tokenJwtRepo.setIsExpired(username);
                throw new BadCredentialsException("Invalid token from database or from client");
            }
        }
        // sau khi validate. điều này có nghiã token vẫn còn hiệu lực truy cập.
        // lưu vào context để dùng cho các thao tác tiếp theo.
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        usernamePasswordAuthenticationToken.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        log.info("-----end validate filter-------");
        filterChain.doFilter(request,response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().equals("/api/v1/admin/login");
    }
}
