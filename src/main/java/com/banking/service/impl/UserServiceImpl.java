package com.banking.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.banking.dto.UserDTO;
import com.banking.exception.WrongTokenException;
import com.banking.exception.WrongUserNamePasswordException;
import com.banking.model.Authentication;
import com.banking.model.User;
import com.banking.repository.AuthenticationRepository;
import com.banking.repository.UserRepository;
import com.banking.service.UserService;
import com.banking.util.TokenGeneratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("userService")
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Override
    public User findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUserById(long id) {
        userRepository.deleteById((int) id);
    }

    @Override
    @Transactional
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public List<UserDTO> convertToUsersListDTO() {
        List<UserDTO> userDTOList = new ArrayList<>();
        List<User> userList = userRepository.findAll();
        for (User user: userList) {
            userDTOList.add(new UserDTO(user.getId(), user.getUserName(), user.getCreatedTime(), user.getUpdatedTime()));
        }
        return userDTOList;
    }

    @Override
    @Transactional
    public String loginUser(String username, String password) throws WrongUserNamePasswordException {
        Optional<User> verifyUser = verifyUserPassword(username, password);
        if (!verifyUser.isPresent()){
            throw new WrongUserNamePasswordException("User not found in the database!");
        }
        String generateToken = TokenGeneratorUtils.generateToken();
        Authentication getAuthenticationByToken = authenticationRepository.findAuthenticationByToken(generateToken);
        LocalDateTime creationTime = LocalDateTime.now();
        if (getAuthenticationByToken != null){
            String newGenerateToken = TokenGeneratorUtils.generateToken();
            LocalDateTime newCreationTime = LocalDateTime.now();
            authenticationRepository.save(new Authentication(newGenerateToken, verifyUser.get(), newCreationTime));
            return newGenerateToken;
        }
        authenticationRepository.save(new Authentication(generateToken, verifyUser.get(), creationTime));
        return generateToken;
    }

    @Override
    public Optional<User> verifyUserPassword(String username, String password){

        if (findUserByUserNameAndPassword(username, password) != null){
            return Optional.of(findUserByUserNameAndPassword(username, password));
        }
        return Optional.empty();
    }

    @Override
    public User findUserByUserNameAndPassword(String username, String password){
        return userRepository.findUserByUserNameAndPassword(username, password);
    }

    @Override
    @Transactional
    public void logoutUser(String token) throws WrongTokenException {
        Authentication authentication = authenticationRepository.findByToken(token);
        if (authentication != null){
            authenticationRepository.delete(authentication);
            authenticationRepository.flush();
            throw new ResponseStatusException(HttpStatus.OK, "Token deleted!");
        }
        throw new WrongTokenException("Wrong token!");
    }

}
