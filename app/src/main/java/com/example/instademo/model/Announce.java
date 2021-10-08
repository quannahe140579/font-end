package com.example.instademo.model;
import com.example.instademo.dto.FriendDTO;
import com.example.instademo.dto.PostDTO;
import com.example.instademo.dto.UserDTO;

import java.io.Serializable;

import lombok.Data;

@Data
public class Announce implements Serializable {
	private long id;
	private String title;
	private long user_id;
	private long post_id;
	private long friend_id;
}
