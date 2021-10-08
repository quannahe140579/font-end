package com.example.instademo.model;

import com.example.instademo.dto.FriendDTO;
import com.example.instademo.dto.PostDTO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class User implements Serializable {
  private long id;
  private String username;
  private String password;
  private boolean isActive;
  private Date createdDate;
  private String fullName;
  private Date dateOfBirth;
  private String phone;
  private String address;
  private List<Friend> listFriend;
  private List<Post> listPos;
  private String avatar;
  private List<Announce> listAnnounce;
}
