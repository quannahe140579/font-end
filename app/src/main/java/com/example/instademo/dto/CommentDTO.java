package com.example.instademo.dto;

import lombok.Data;

@Data
public class CommentDTO {
	private long id;
	private long post_id;
	private String content;
	private String friendName;
	private String urlAvt;
	private long friend_id;
}
