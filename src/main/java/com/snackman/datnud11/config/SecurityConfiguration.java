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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import java.util.Arrays;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfiguration {
  @Autowired
  private JwtAuthenticationFilter jwtAuthFilter;
  @Autowired
  private AuthenticationProvider authenticationProvider;
  @Autowired
  private UserDetailsService userDetailsService;
  @Autowired
  private LogoutHandler logoutHandler;
  private LogoutService logoutService;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.cors()
            .and()
        .csrf()
            .disable()
        .authorizeHttpRequests()
            .requestMatchers("/api/v1/account/**").hasAuthority("ADMIN_ROLE")
            .requestMatchers("/api/v1/admin/login","/api/v1/products/**","/api/v1/inventory/**","/api/v1/payment/**")
            .permitAll()
            .requestMatchers("/api/v1/card").hasAuthority("CLIENT_ROLE")
            .anyRequest()
            .authenticated()
            .and()
            .rememberMe().userDetailsService(userDetailsService)
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
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
