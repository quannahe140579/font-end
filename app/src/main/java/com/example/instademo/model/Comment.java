package com.example.instademo.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class Comment implements Serializable {
	private long id;
	private long post_id;
	private String content;
	private String friendName;
}
