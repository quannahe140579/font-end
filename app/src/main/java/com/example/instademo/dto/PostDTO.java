package com.example.instademo.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class PostDTO {
	private long id;
	private String content;
	private List<ImageDTO> listImage;
	private List<CommentDTO> listComment;
	private Date createdDate;
	private int totalLike;
	private boolean isActive;
	private long user_id;
	private String userName;
}
