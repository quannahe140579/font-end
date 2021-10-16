package com.example.instademo.mapper;

import com.example.instademo.dto.CommentDTO;
import com.example.instademo.model.Comment;


public class CommentMapper {
//	public static CommentDTO _toDTO(Comment entity) {
//		CommentDTO dto = new CommentDTO();
//		dto.setId(entity.getId());
//		dto.setPost_id(entity.getPost().getId());
//		dto.setContent(entity.getContent());
//		return dto;
//	}

    public static Comment _toModel(CommentDTO dto) {
		Comment model = new Comment();
		model.setId(dto.getId());
		model.setPost_id(dto.getPost_id());
		model.setContent(dto.getContent());
		model.setFriendName(dto.getFriendName());
		return model;
    }

    public static CommentDTO _toDTO(Comment c) {
		CommentDTO dto = new CommentDTO();
		dto.setId(c.getId());
		dto.setPost_id(c.getPost_id());
		dto.setContent(c.getContent());
		dto.setFriendName(c.getFriendName());
		return dto;
    }
}
