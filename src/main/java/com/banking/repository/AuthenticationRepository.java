package com.banking.repository;

import com.banking.model.Authentication;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationRepository extends GenericRepository<Authentication> {
    Authentication findAuthenticationByToken(String token);
    Authentication findAuthenticationById(Long id);
    Authentication findByToken(String token);
}
