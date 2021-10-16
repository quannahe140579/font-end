package com.example.instademo.mapper;

import com.example.instademo.dto.FriendDTO;
import com.example.instademo.model.Friend;


public class FriendMapper {
//	public static FriendDTO _toDTO(Friend entity) {
//		FriendDTO dto = new FriendDTO();
//		dto.setId(entity.getId());
//		dto.setUsername(entity.getUsername());
//		return dto;
//	}

    public static Friend _toModel(FriendDTO friendDTO) {
		Friend model = new Friend();
		model.setId(friendDTO.getId());
		model.setUsername(friendDTO.getUsername());
		return model;
    }

    public static FriendDTO _toDTO(Friend model) {
        FriendDTO dto = new FriendDTO();
        dto.setId(model.getId());
        dto.setUsername(model.getUsername());
        return dto;
    }
}
