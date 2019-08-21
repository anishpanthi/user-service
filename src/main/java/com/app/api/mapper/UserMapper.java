package com.app.api.mapper;

import com.app.api.domain.User;
import com.app.api.dto.UserDto;
import org.springframework.beans.BeanUtils;

import java.util.function.Function;

/**
 * @author Anish Panthi
 */
public class UserMapper {

    private UserMapper(){
    }

    private static Function<User, UserDto> userToUserDto = user -> {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        return userDto;
    };

    public static UserDto mapToDto(User user) {
        return userToUserDto.apply(user);
    }
}
