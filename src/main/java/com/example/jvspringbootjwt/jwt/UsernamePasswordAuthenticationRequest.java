package com.example.jvspringbootjwt.jwt;

import lombok.Data;

@Data
public class UsernamePasswordAuthenticationRequest {
    private String username;
    private String password;
}
