package com.banking.repository;

import com.banking.model.Authentication;
import com.banking.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationRepository extends GenericRepository<Authentication> {
    Authentication findAuthenticationByToken(String token);
    Authentication findAuthenticationById(Long id);
}
