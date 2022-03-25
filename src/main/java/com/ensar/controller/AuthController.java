package com.ensar.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.ensar.entity.User;
import com.ensar.request.dto.LoginUserDto;
import com.ensar.response.dto.AuthTokenResponse;
import com.ensar.service.UserService;
import com.ensar.util.JwtTokenUtil;

import javax.validation.Valid;

import static com.ensar.util.Constants.ACCOUNT_DEACTIVATED_MESSAGE;
import static com.ensar.util.Constants.DATA_KEY;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "Authentication")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/auth")
public class AuthController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    public AuthController(
            AuthenticationManager authenticationManager,
            JwtTokenUtil jwtTokenUtil,
            UserService userService
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }


    @ApiOperation(value = "User Login")
    @PostMapping(value = "/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginUserDto loginUserDto)
            throws ResourceNotFoundException {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUserDto.getEmail(),
                        loginUserDto.getPassword()
                )
        );

        User user = userService.getUserByEmail(loginUserDto.getEmail());

        Map<String, String> result = new HashMap<>();

        if (user.isDisabled()) {
            result.put(DATA_KEY, ACCOUNT_DEACTIVATED_MESSAGE);

            return ResponseEntity.badRequest().body(result);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = jwtTokenUtil.createTokenFromAuth(authentication);
        Date expirationDate = jwtTokenUtil.getExpirationDateFromToken(token);

        return ResponseEntity.ok(new AuthTokenResponse(token, expirationDate.getTime(), user.getRole()));
    }

}
