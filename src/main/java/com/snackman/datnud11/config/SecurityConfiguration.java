package com.snackman.datnud11.config;

import com.snackman.datnud11.filters.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;


import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfiguration {

  private final JwtAuthenticationFilter jwtAuthFilter;
  private final AuthenticationProvider authenticationProvider;
  private final LogoutHandler logoutHandler;
  private LogoutService logoutService;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    log.info("security config");
    http
        .csrf()
            .disable()
        .authorizeHttpRequests()
<<<<<<< Updated upstream
            .requestMatchers("/api/v1/admin/management/**","/api/v1/category").hasRole("ADMIN_ROLE")
            .requestMatchers("/api/v1/admin/login","/api/v1/products/**","/api/v1/client/**","/api/v1/inventory/**","/api/v1/payment/**")
            .permitAll()
            .requestMatchers("/api/v1/card").hasRole("CLIENT_ROLE")
            .anyRequest()
            .authenticated()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
=======
        .requestMatchers("/api/v1/auth/authenticate","/api/v1/products/**","/api/v1/client/**","/api/v1/inventory/**","/api/v1/payment/**",
                "/api/v1/bills/**")
          .permitAll()
        .anyRequest()
          .authenticated()
        .and()
          .sessionManagement()
          .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
>>>>>>> Stashed changes
            .logout()
            .logoutUrl("/api/v1/admin/users/logout")
            .addLogoutHandler(logoutHandler)
            .logoutSuccessHandler((request,response,authentication)-> SecurityContextHolder.clearContext());

    return http.build();
  }
}
