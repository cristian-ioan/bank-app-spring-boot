package com.banking.dto.converter;

import com.banking.dto.UserDTO;
import com.banking.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    public User convertFromUserDTO(UserDTO userDto) {
        return new User(userDto.getName(), null, userDto.getCreatedTime(), userDto.getUpdatedTime());
    }

    public UserDTO convertToUserDTO(User user) {
        return new UserDTO(user.getId(), user.getUserName(), user.getCreatedTime(), user.getUpdatedTime());
    }

}
