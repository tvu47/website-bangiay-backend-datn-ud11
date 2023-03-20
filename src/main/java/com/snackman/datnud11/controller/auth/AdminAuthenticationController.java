package com.snackman.datnud11.controller.auth;

import com.snackman.datnud11.dto.request.UserLoginRequest;
import com.snackman.datnud11.exceptions.BadLoginException;
import com.snackman.datnud11.exceptions.JwtTokenException;
import com.snackman.datnud11.exceptions.RoleNotFoundException;
import com.snackman.datnud11.exceptions.UserNotfoundException;
import com.snackman.datnud11.responses.AdminUserResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminAuthenticationController {
  private final UserAuthenticationService service;
  @PostMapping("/login")
  public ResponseEntity<AdminUserResponse> login(@Valid @RequestBody UserLoginRequest request) throws UserNotfoundException, RoleNotFoundException, BadLoginException, JwtTokenException {
    AdminUserResponse adminUserResponse = service.getAdminLogin(request);
    return new ResponseEntity<>(adminUserResponse, HttpStatus.OK);
  }
  @GetMapping("/home")
  public ResponseEntity<String> loginSuccess(){
    return ResponseEntity.ok("login ok ...");
  }
  @GetMapping("/error")
  public ResponseEntity<String> loginError(){
    return ResponseEntity.ok("error somewhere ...");
  }
  @PostMapping("/logout")
  public ResponseEntity<String> logout() {
        return ResponseEntity.ok(service.toAdminLogout());
  }
}
