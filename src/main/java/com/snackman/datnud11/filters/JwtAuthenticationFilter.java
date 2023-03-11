package com.snackman.datnud11.filters;

import com.snackman.datnud11.consts.FilterTypeConstant;
import com.snackman.datnud11.filters.impl.AbstractUserResponseFilter;
import com.snackman.datnud11.filters.impl.response.ResponseFilterFactory;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  @Autowired
  private ResponseFilterFactory responseFilterFactory;
  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain
  ) throws ServletException, IOException {
    final String authHeader = request.getHeader("Authorization");

    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      log.error("auth header is require.. ");
      filterChain.doFilter(request, response);
      return;
    }
    AbstractUserResponseFilter filterService = responseFilterFactory.get(FilterTypeConstant.CONTENT_TYPE_CLIENT_USER);
    filterService.run(request);

    filterChain.doFilter(request, response);
  }
}
