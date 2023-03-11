package com.snackman.datnud11.controller.auth;

import com.snackman.datnud11.config.JwtService;
import com.snackman.datnud11.dto.request.ClientRegisterRequest;
import com.snackman.datnud11.entity.Customers;
import com.snackman.datnud11.repo.CustomersRepository;
import com.snackman.datnud11.responses.ClientInformationResponse;
import com.snackman.datnud11.services.CustomerService;
import com.snackman.datnud11.services.auth.ClientAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientAuthenticationService {
  private final CustomerService customerService;
  private final CustomersRepository customersRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public ClientInformationResponse register(ClientRegisterRequest request) {
    Customers customers = customerService.findCustomerByEmail(request.getEmail());
    if (customers != null){
      return null;
    }

    var user = ClientAuth.builder().email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .roles(List.of("CLIENT_ROLE"))
        .build();
    Customers customers1 = new Customers();
    customers1.setEmail(user.getEmail());
    customers1.setPassword(user.getPassword());
    customersRepository.save(customers1);
    return ClientInformationResponse.builder()
        .email(request.getEmail())
        .build();
  }
  public ClientInformationResponse clientLogin(ClientRegisterRequest request) {
    try {
      authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                      request.getEmail(),
                      request.getPassword()
              )
      );
    }catch (Exception e){
      e.printStackTrace();
    }
    Customers customers = customerService.findCustomerByEmail(request.getEmail());
    if (customers == null) {
      throw new UsernameNotFoundException("user not found exception");
    }
    ClientAuth clientAuth = new ClientAuth();
    clientAuth.setEmail(customers.getEmail());
    clientAuth.setPassword(customers.getPassword());
    clientAuth.setRoles(List.of("CLIENT_ROLE"));
    var jwtToken = jwtService.generateToken(clientAuth);
    return ClientInformationResponse.builder()
            .email(customers.getEmail())
            .token(jwtToken)
            .build();
  }

}
