package com.example.instademo.mapper;

import com.example.instademo.dto.AnnounceDTO;
import com.example.instademo.model.Announce;

public class AnnounceMapper {
//	public static AnnounceDTO _toDTO(Announce entity) {
//		AnnounceDTO dto = new AnnounceDTO();
//		dto.setId(entity.getId());
//		dto.setTitle(entity.getTitle());
//		dto.setPost(PostMapper._toDTO(entity.getPost()));
//		dto.setFriend(FriendMapper._toDTO(entity.getFriend()));
//		dto.setUser(UserMapper._toDTO(entity.getUser()));
//		return dto;
//	}
	public static Announce _toModel(AnnounceDTO dto) {
		Announce model = new Announce();
		model.setId(dto.getId());
		model.setTitle(dto.getTitle());
		model.setPost_id(dto.getPost_id());
		model.setFriend_id(dto.getFriend_id());
		model.setUser_id(dto.getUser_id());
		return model;
	}

    public static AnnounceDTO _toDTO(Announce model) {
		AnnounceDTO dto = new AnnounceDTO();
		dto.setId(model.getId());
		dto.setTitle(model.getTitle());
		dto.setPost_id(model.getPost_id());
		dto.setFriend_id(model.getFriend_id());
		dto.setUser_id(model.getUser_id());
		return dto;
    }
}
