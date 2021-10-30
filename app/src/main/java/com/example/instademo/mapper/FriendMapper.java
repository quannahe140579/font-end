package com.example.instademo.mapper;

import com.example.instademo.dto.FriendDTO;
import com.example.instademo.model.Friend;

import java.util.ArrayList;
import java.util.List;


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
		model.setFullName(friendDTO.getFullName());
		model.setUrlAvt(friendDTO.getUrlAvt());
		return model;
    }

    public static FriendDTO _toDTO(Friend model) {
        FriendDTO dto = new FriendDTO();
        dto.setId(model.getId());
        dto.setUsername(model.getUsername());
        dto.setUrlAvt(model.getUrlAvt());
        dto.setFullName(model.getFullName());
        return dto;
    }

    public static List<Friend> _toListModel(List<FriendDTO> listDto) {
        List<Friend> list = new ArrayList<>();
        for (FriendDTO dto: listDto
             ) {
            list.add(_toModel(dto));
        }
        return list;
    }
}
