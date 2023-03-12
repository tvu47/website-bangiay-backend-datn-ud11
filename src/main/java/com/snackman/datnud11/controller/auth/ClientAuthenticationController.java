package com.snackman.datnud11.controller.auth;

import com.snackman.datnud11.dto.request.ClientLoginRequest;
import com.snackman.datnud11.dto.request.ClientRegisterRequest;
import com.snackman.datnud11.exceptions.UserExistedException;
import com.snackman.datnud11.exceptions.UserNotfoundException;
import com.snackman.datnud11.responses.ClientInformationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/client")
@RequiredArgsConstructor
public class ClientAuthenticationController {

  private final ClientAuthenticationService service;

  @PostMapping("/register")
  public ResponseEntity<ClientInformationResponse> register(
      @Valid @RequestBody ClientRegisterRequest request
  ) throws UserExistedException {
    return ResponseEntity.ok(service.register(request));
  }
  @PostMapping("/login")
  public ResponseEntity<ClientInformationResponse> login(
          @Valid @RequestBody ClientLoginRequest request
  ) throws UserNotfoundException {
    return ResponseEntity.ok(service.clientLogin(request));
  }
//  @PostMapping("/logout")
//  public ResponseEntity<ClientInformationResponse> logout(
//          @RequestBody AuthenticationRequest request
//  ) {
//    return ResponseEntity.ok(service.authenticate(request));
//  }
}
