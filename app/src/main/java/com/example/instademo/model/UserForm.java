package com.example.instademo.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserForm {
    private String username;
    private String fullName;
    private String address;
    private String birthDate;
    private String phone;
    private byte[] avatar;
}
