package com.banking.service.impl;

import com.banking.exception.BalanceException;
import com.banking.exception.DetailsAccountException;
import com.banking.exception.WrongUserNamePasswordException;
import com.banking.model.Authentication;
import com.banking.model.User;
import com.banking.repository.UserRepository;
import com.banking.service.UserService;
import com.banking.util.TokenGeneratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service("userService")
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    private User user;
    private Optional<User> resultOfUserVerification;
    private IOService ioService = IOService.getInstance();

    private final static Logger LOG = Logger.getLogger(Logger.class.getName());

    @Autowired
    private UserRepository userRepository;

    private AuthenticationServiceImpl authenticationServiceImpl;

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
    @Transactional
    public void loginUser(String username, String password) {
        Optional<User> verifyUser = verifyUserPassword(username, password);
        if (verifyUser.isPresent()){
            String generateToken = TokenGeneratorUtils.generateToken();
            LocalDateTime creationTime = LocalDateTime.now();
            authenticationServiceImpl.createAuthentication(new Authentication(generateToken, verifyUser.get(), creationTime));
        }
    }

    @Override
    public User findUserByUserNameAndPassword(String username, String password){
        return userRepository.findUserByUserNameAndPassword(username, password);
    }

    @Override
    public Optional<User> verifyUserPassword(String username, String password){

        if (findUserByUserNameAndPassword(username, password) != null){
            return Optional.of(findUserByUserNameAndPassword(username, password));
        }
        return Optional.empty();
    }

}
