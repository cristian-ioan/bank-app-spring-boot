package com.banking.controller;

import com.banking.exception.WrongTokenException;
import com.banking.exception.WrongUserNamePasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.banking.dto.UserDTO;
import com.banking.dto.converter.UserConverter;
import com.banking.service.UserService;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserConverter userConverter;

    @GetMapping("/get/{id}")
    public UserDTO getUser(@PathVariable(value = "id") Long id)
    {
        return userConverter.convertToUserDTO(userService.findById(id));
    }

    @GetMapping(path = "/get-all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDTO> getAll(){
        return userService.convertToUsersListDTO();
    }

    @PostMapping("/loginUser/{username}/&/{password}")
    @ResponseBody
    public String loginUser(@PathVariable("username") String username,
                                    @PathVariable("password") String password) {
        try{
            return userService.loginUser(username, password);
        } catch (WrongUserNamePasswordException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!!!", ex);
        }
    }

    @DeleteMapping("/logout/{token}")
    @ResponseBody
    public String logoutUser(@PathVariable("token") String token){
        try {
            return userService.logoutUser(token);
        } catch (WrongTokenException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Token not found!!!", ex);
        }
    }

}
