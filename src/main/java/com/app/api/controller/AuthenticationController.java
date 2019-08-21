package com.app.api.controller;

import com.app.api.domain.User;
import com.app.api.dto.LoginRequestDto;
import com.app.api.dto.LoginResponseDto;
import com.app.api.dto.UserDto;
import com.app.api.exception.DataException;
import com.app.api.exception.NotFoundException;
import com.app.api.security.dto.JwtUserDto;
import com.app.api.security.util.JwtTokenGenerator;
import com.app.api.service.UserService;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author Anish Panthi
 */
@RestController
@Log4j2
@Api(value = "Authentication Controller", tags = "Operations pertaining to User's authentication.")
public class AuthenticationController {

    @Value("${jwt.secret}")
    private String secret;

    private final UserService userService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping(value = "/auth", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<? extends Object> getAuthentication(@RequestBody @Valid LoginRequestDto loginRequestDto, BindingResult result) {

        if (result.hasErrors()) {
            throw new DataException("Data Validation Error!!!", result);
        }

        Optional<User> loggedInUser = userService.findByUsername(loginRequestDto.getUsername());
        UserDto loggedInUserDto = new UserDto();
        log.info("{}", loggedInUser.isPresent());
        if (!loggedInUser.isPresent()) {
            log.error("User not found!!!");
            throw new NotFoundException("User not Found!!!");
        } else {
            BeanUtils.copyProperties(loggedInUser.get(), loggedInUserDto);
        }
        log.info("Logged in User password: {}", loggedInUserDto.getPassword());
        String token = "";
        if (this.bCryptPasswordEncoder.matches(loginRequestDto.getPassword(), loggedInUserDto.getPassword())) {
            token = JwtTokenGenerator.generateToken(new JwtUserDto(loggedInUserDto.getId(), loggedInUserDto.getUsername(), "ROLE_ADMIN"), secret);
        }

        final LoginResponseDto loginResponseDto = new LoginResponseDto(loggedInUserDto.getId(), token, "ROLE_ADMIN");
        loginResponseDto.add(linkTo(methodOn(AuthenticationController.class).getAuthentication(loginRequestDto, result)).withSelfRel());

        return new ResponseEntity<>(loginResponseDto, HttpStatus.ACCEPTED);
    }

    @Autowired
    public AuthenticationController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
}
