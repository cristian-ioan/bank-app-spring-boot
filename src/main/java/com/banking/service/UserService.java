package com.banking.service;

import com.banking.dto.UserDTO;
import com.banking.exception.WrongTokenException;
import com.banking.exception.WrongUserNamePasswordException;
import com.banking.model.User;
import org.springframework.stereotype.Service;

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

    List<UserDTO> convertToUsersListDTO();
    String loginUser(String username, String password) throws WrongUserNamePasswordException;
    String logoutUser(String token) throws WrongTokenException;
    User findUserByUserNameAndPassword(String username, String password);
    Optional<User> verifyUserPassword(String username, String password);
}
