package com.snackman.datnud11.controller.auth;

import com.snackman.datnud11.config.JwtService;
import com.snackman.datnud11.config.SecurityConfiguration;
import com.snackman.datnud11.dto.request.UserLoginRequest;
import com.snackman.datnud11.dto.request.UserRegisterRequest;
import com.snackman.datnud11.entity.Customers;
import com.snackman.datnud11.exceptions.*;
import com.snackman.datnud11.repo.CustomersRepository;
import com.snackman.datnud11.responses.AdminUserResponse;
import com.snackman.datnud11.responses.ClientInformationResponse;
import com.snackman.datnud11.services.CustomerService;
import com.snackman.datnud11.services.UserService;
import com.snackman.datnud11.services.auth.UserAuth;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserAuthenticationService {
  private final UserService userService;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  public AdminUserResponse getAdminLogin(UserLoginRequest request) throws UserNotfoundException, RoleNotFoundException, BadLoginException {
    System.out.println("security context 2.2 jwt: "+ SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    try {
      authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                      request.getUsername(),
                      request.getPassword()
              )
      );
    }catch (Exception e){
      e.printStackTrace();
      throw new BadLoginException("Username or password is wrong.");
    }
    UserAuth userAuth = (UserAuth) userService.getUserDetailFromDB(request.getUsername());
    var jwtToken = jwtService.generateToken(userAuth);

    return AdminUserResponse.builder()
            .username(userAuth.getUsername())
            .token(jwtToken)
            .roles(userAuth.getRoles())
            .build();
  }

  @CacheEvict(value = "jwt_token_authen")
  public String toAdminLogout(){
    String status = "false";
    try {
      System.out.println("security context 3 jwt: "+ SecurityContextHolder.getContext().getAuthentication());
      SecurityContext context = SecurityContextHolder.getContext();
      SecurityContextHolder.clearContext();
      context.setAuthentication(null);
      status = "true";
    }catch (Exception e){
      e.printStackTrace();
    }
    return status;
  }
}