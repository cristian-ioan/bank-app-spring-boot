package com.banking.service;

import com.banking.exception.BalanceException;
import com.banking.exception.DetailsAccountException;
import com.banking.exception.WrongUserNamePasswordException;
import com.banking.model.User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    User findById(Long id);
    List<User> findAll();
    User createUser(User user);
    User updateUser(User user);
    void deleteUserById(long id);
    void deleteUser(User user);

    void loginUser(String username, String password);
    User findUserByUserNameAndPassword(String username, String password);
    Optional<User> verifyUserPassword(String username, String password);
}
