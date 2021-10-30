package com.example.instademo.model;

import lombok.Data;

@Data
public class PostForm {
    private String content;
    private byte[] byteAvt;
    private long user_id;
}
