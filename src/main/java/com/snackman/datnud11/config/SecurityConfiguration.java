package com.snackman.datnud11.config;

import com.snackman.datnud11.filters.*;
import com.snackman.datnud11.filters.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfiguration {
  @Autowired
  private GenerateJwtTokenFilter generateJwtTokenFilter;
  @Autowired
  private ValidateJwtTokenFilter validateJwtTokenFilter;
  @Autowired
  private UserDetailsService userDetailsService;
  @Autowired
  private AuthenticationProvider authenticationProvider;
  private final AuthenticationConfiguration configuration;

  @Autowired
  private LogoutHandler logoutHandler;
  private LogoutService logoutService;

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.cors()
            .and()
        .csrf()
            .disable()
        .authorizeHttpRequests()
            .requestMatchers("/api/v1/account/**", "/api/v1/admin/logout","/api/v1/category").hasAuthority("ADMIN_ROLE")
            .requestMatchers("/api/v1/admin/login","/api/v1/customers/login","/api/v1/customers/register","/api/v1/products/**","/api/v1/inventory/**","/api/v1/payment/**")
            .permitAll()
            .requestMatchers("/api/v1/card").hasAuthority("CLIENT_ROLE")
            .anyRequest()
            .authenticated()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(validateJwtTokenFilter, BasicAuthenticationFilter.class)
            .addFilterBefore(new JwtAuthenticationFilter(authenticationManager(configuration), userDetailsService), JwtAuthenticationFilter.class)
            .addFilterAfter(generateJwtTokenFilter, BasicAuthenticationFilter.class)
            .logout()
            .logoutUrl("/api/v1/admin/users/logout")
            .addLogoutHandler(logoutHandler)
            .logoutSuccessHandler((request,response,authentication)-> SecurityContextHolder.clearContext());
    return http.build();
  }


  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
    configuration.setAllowedMethods(Arrays.asList("GET","POST","PATCH", "PUT", "DELETE", "OPTIONS", "HEAD"));
    configuration.setAllowCredentials(true);
    configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
    configuration.setMaxAge(3600L);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
