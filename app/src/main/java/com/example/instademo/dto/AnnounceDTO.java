package com.example.instademo.dto;
import lombok.Data;

@Data
public class AnnounceDTO {
	private long id;
	private String title;
	private long user_id;
	private long post_id;
	private long friend_id;
}
