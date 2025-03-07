package com.test.wepapp.dto;

import lombok.Data;

@Data
public class UserToRegisterDto {
    private String username;
    private String email;
    private String password;
    private String role;
}
