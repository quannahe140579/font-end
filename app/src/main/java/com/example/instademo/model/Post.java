package com.example.instademo.model;

import com.example.instademo.dto.CommentDTO;
import com.example.instademo.dto.ImageDTO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Post implements Serializable {
	private long id;
	private String content;
	private List<Image> listImage;
	private List<Comment> listComment;
	private Date createdDate;
	private int totalLike;
	private boolean isActive;
	private long user_id;
	private String userName;
}
