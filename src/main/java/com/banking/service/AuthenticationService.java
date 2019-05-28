package com.banking.service;

import com.banking.model.Authentication;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {

    Authentication findAuthenticationByToken(String token);
    Authentication findAuthenticationById(Long id);
    Authentication createAuthentication(Authentication authentication);
    Authentication updateAuthentication(Authentication authentication);
    void deleteAuthentication(Authentication authentication);

}
