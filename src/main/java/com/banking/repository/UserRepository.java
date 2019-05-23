package com.banking.repository;

import com.banking.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends GenericRepository<User> {
    User findUserById(long id);
    User findUserByUserNameAndPassword(String username, String password);
}