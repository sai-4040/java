package com.ensar.response.dto;

import com.ensar.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@AllArgsConstructor
@Setter
@Getter
public class AuthTokenResponse {
    private String accessToken;
    private long expiresIn;
    private User.Role role;
}


