package com.banking.dto.converter;

import com.banking.dto.UserDTO;
import com.banking.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserConverter {

    public User convertFromUserDTO(UserDTO userDto) {
        return new User(userDto.getName(), null);
    }

    public UserDTO convertToUserDTO(User user) {
        return new UserDTO(user.getUserName());
    }

}
