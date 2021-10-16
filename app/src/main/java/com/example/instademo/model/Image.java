package com.example.instademo.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class Image implements Serializable {
	private long id;
	private String name;
	private long post_id;
}
