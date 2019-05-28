package com.banking.service.impl;

import com.banking.model.Authentication;
import com.banking.repository.AuthenticationRepository;
import com.banking.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("authenticationService")
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Override
    @Transactional
    public Authentication createAuthentication(Authentication authentication) {
        return authenticationRepository.save(authentication);
    }

    @Override
    @Transactional
    public Authentication updateAuthentication(Authentication authentication) {
        return authenticationRepository.save(authentication);
    }

    @Override
    @Transactional
    public void deleteAuthentication(Authentication authentication) {
        authenticationRepository.delete(authentication);
    }

    @Override
    public Authentication findAuthenticationByToken(String token) {
        return authenticationRepository.findAuthenticationByToken(token);
    }

    @Override
    public Authentication findAuthenticationById(Long id) {
        return authenticationRepository.findAuthenticationById(id);
    }
}
