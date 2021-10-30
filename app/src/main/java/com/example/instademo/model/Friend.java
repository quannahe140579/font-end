package com.example.instademo.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class Friend implements Serializable {
	private long id;
	private String username;
	private String urlAvt;
	private String fullName;
}
