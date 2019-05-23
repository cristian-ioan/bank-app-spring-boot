package com.banking.service;

import com.banking.model.Authentication;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {

    String findAuthenticationByToken(String token);
    Authentication createAuthentication(Authentication authentication);
    Authentication updateAuthentication(Authentication authentication);
    void deleteAuthentication(Authentication authentication);

}
