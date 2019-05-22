package com.app.api.config;

import com.app.api.dto.UserDto;
import com.app.api.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

/**
 * @author Anish Panthi
 */
@Configuration
@Slf4j
public class DataLoader implements CommandLineRunner {

    private UserService userService;

    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args) throws Exception {
        UserDto userDto = new UserDto();
        userDto.setFirstName("Anish");
        userDto.setLastName("Panthi");
        userDto.setEmail("anish.panthi@gmail.com");
        userDto.setUsername("anish.panthi");
        userDto.setContact("1234567890");
        userDto.setPassword("$2a$10$/7Ano/rYlliPs77q9IL3HeVArI2VdFZXnQySqzyYui86RK0Ui3BrC");    // anish123
        userService.save(userDto);
    }

    @Autowired
    public DataLoader(UserService userService) {
        this.userService = userService;
    }
}
