package com.banking.controller;

import javax.websocket.server.PathParam;
import javax.xml.ws.WebServiceException;

import com.banking.exception.WrongUserNamePasswordException;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.banking.dto.UserDTO;
import com.banking.dto.converter.UserConverter;
import com.banking.model.User;
import com.banking.service.UserService;

import java.util.List;

@RestController("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserConverter userConverter;

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO getUser(@PathVariable(value = "id") Long id) {
        return userConverter.convertToUserDTO(userService.findById(id));
    }

    @GetMapping("/get-all")
    public List<User> findAll(){
        return userService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO createUser(@RequestBody User user) {
        return userConverter.convertToUserDTO(userService.createUser(user));
    }

    @PutMapping
    public ResponseEntity<UserDTO> updateUser(@RequestBody User user) {

        User updatedUser = userService.updateUser(user);
        if (updatedUser != null) {
            return new ResponseEntity<>(userConverter.convertToUserDTO(userService.updateUser(updatedUser)),
                    HttpStatus.OK);
        }

        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@RequestParam Long id) {
        userService.deleteUserById(id);
    }

    @GetMapping("/error")
    public UserDTO getError() throws Exception {
        throw new Exception();
    }

    @PutMapping("/loginUser/{username}&{password}")
    @ResponseStatus(HttpStatus.CREATED)
    public void loginUser(@PathVariable(value = "username") String username,
                          @PathVariable(value = "password") String password) {
        userService.loginUser(username, password);
    }

}
