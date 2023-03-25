package com.snackman.datnud11.controller.auth;

import com.snackman.datnud11.exceptions.*;
import com.snackman.datnud11.responses.AdminUserResponse;
import com.snackman.datnud11.services.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserAuthenticationService {
  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private TokenService tokenService;

  public AdminUserResponse getAdminLogin(String token) throws UserNotfoundException, RoleNotFoundException, BadLoginException, JwtTokenException {
    return AdminUserResponse.builder()
            .token(token)
            .build();
  }

  @Caching(evict = {
          @CacheEvict(value = "user_details", allEntries = true ),
          @CacheEvict(value = "jwt_token", allEntries = true )
  })
  public String toAdminLogout(){
    String status = "false";
    try {
      SecurityContext context = SecurityContextHolder.getContext();
      SecurityContextHolder.clearContext();
      context.setAuthentication(null);
      status = "true";
    } catch (Exception e) {
      e.printStackTrace();
    }
    return status;
  }
//  public String generateTokenFromUserAuthenticated (String username) throws RoleNotFoundException, UserNotfoundException {
//    UserAuth userAuth = (UserAuth) userService.getUserDetailFromDB(username);
//    return jwtService.generateToken(userAuth);
//  }
}
