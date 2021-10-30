package com.example.instademo.utils;

import com.example.instademo.model.Post;
import com.example.instademo.model.User;

import java.util.List;

public class LogedUser {
    public static User logedUser;
    public static List<Post> listPost;

    public LogedUser(User user) {
        logedUser = user;
    }

}
