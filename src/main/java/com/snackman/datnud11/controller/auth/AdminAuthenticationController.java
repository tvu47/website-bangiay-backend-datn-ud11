package com.snackman.datnud11.controller.auth;

import com.snackman.datnud11.dto.request.UserLoginRequest;
import com.snackman.datnud11.exceptions.BadLoginException;
import com.snackman.datnud11.exceptions.RoleNotFoundException;
import com.snackman.datnud11.exceptions.UserNotfoundException;
import com.snackman.datnud11.responses.AdminUserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminAuthenticationController {

  private final UserAuthenticationService service;
  @PostMapping("/login")
  public ResponseEntity<AdminUserResponse> login(@Valid @RequestBody UserLoginRequest request) throws UserNotfoundException, RoleNotFoundException, BadLoginException {
    return ResponseEntity.ok(service.getAdminLogin(request));
  }
  @GetMapping("/home")
  public ResponseEntity<String> loginSuccess(){
    return ResponseEntity.ok("login ok ...");
  }
  @GetMapping("/error")
  public ResponseEntity<String> loginError(){
    return ResponseEntity.ok("error somewhere ...");
  }
//  @PostMapping("/logout")
//  public ResponseEntity<ClientInformationResponse> logout(
//          @RequestBody AuthenticationRequest request
//  ) {
//    return ResponseEntity.ok(service.authenticate(request));
//  }
}
