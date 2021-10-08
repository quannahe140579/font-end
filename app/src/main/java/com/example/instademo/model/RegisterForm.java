package com.example.instademo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterForm {
    private String username;
    private String password;
    private String repassword;
}
