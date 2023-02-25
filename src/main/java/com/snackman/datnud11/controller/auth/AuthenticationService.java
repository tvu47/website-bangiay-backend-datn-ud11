package com.snackman.datnud11.controller.auth;

import com.snackman.datnud11.config.JwtService;
import com.snackman.datnud11.entity.RoleUser;
import com.snackman.datnud11.entity.Token;
import com.snackman.datnud11.entity.Users;
import com.snackman.datnud11.repo.RoleUserRepository;
import com.snackman.datnud11.repo.TokenRepository;
import com.snackman.datnud11.repo.UserRepository;
import com.snackman.datnud11.services.UserServices;
import com.snackman.datnud11.services.auth.UserAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserServices userServices;
  private final UserRepository repository;
  private final RoleUserRepository roleUserRepository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthenticationResponse register(RegisterRequest request) {
    var user = UserAuth.builder().username(request.getUsername())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(List.of("USER_ROLE"))
        .build();
    Users users = new Users();
    users.setUsername(user.getUsername());
    users.setPassword(user.getPassword());
    users.setStatus(true);
    var savedUser = repository.save(users);

    RoleUser roleUser = new RoleUser();
    roleUser.setRole("USER_ROLE");
    roleUser.setUsername(user.getUsername());
    roleUserRepository.save(roleUser);
    var jwtToken = jwtService.generateToken(user);
    saveUserToken(savedUser.getId(), jwtToken);
    return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getUsername(),
            request.getPassword()
        )
    );
    Users user = repository.findUserByUsername(request.getUsername());
    if (user == null) {
      throw new UsernameNotFoundException("user not found exception");
    }
    UserAuth userAuth = new UserAuth();
    userAuth.setUsername(user.getUsername());
    userAuth.setPassword(user.getPassword());
    userAuth.setRole(userServices.getRoleListByUsername(user.getUsername()));
    var jwtToken = jwtService.generateToken(userAuth);
    revokeAllUserTokens(user.getId());
    saveUserToken(user.getId(), jwtToken);
    return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
  }
  public AuthenticationResponse login(AuthenticationRequest request) {
    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
            )
    );
    Users user = repository.findUserByUsername(request.getUsername());
    if (user == null) {
      throw new UsernameNotFoundException("user not found exception");
    }
    UserAuth userAuth = new UserAuth();
    userAuth.setUsername(user.getUsername());
    userAuth.setPassword(user.getPassword());
    userAuth.setRole(userServices.getRoleListByUsername(user.getUsername()));
    var jwtToken = jwtService.generateToken(userAuth);
    revokeAllUserTokens(user.getId());
    saveUserToken(user.getId(), jwtToken);
    return AuthenticationResponse.builder()
            .token(jwtToken)
            .build();
  }

  private void saveUserToken(Long userId, String jwtToken) {
    var token = Token.builder()
        .userId(userId)
        .token(jwtToken)
        .tokenType("BEARER")
        .expired(false)
        .revoked(false)
        .build();
    tokenRepository.save(token);
  }

  private void revokeAllUserTokens(Long userId) {
    var validUserTokens = tokenRepository.findAllValidTokenByUser(userId);
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }
}
