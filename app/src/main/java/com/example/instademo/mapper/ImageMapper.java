package com.example.instademo.mapper;

import com.example.instademo.dto.ImageDTO;
import com.example.instademo.model.Image;


public class ImageMapper {
//	public static ImageDTO _toDTO(Image entity) {
//		ImageDTO dto = new ImageDTO();
//		dto.setId(entity.getId());
//		dto.setName(entity.getName());
//		dto.setData(entity.getData());
//		//dto.setPost(PostMapper._toDTO(entity.getPost()));
//		dto.setPost_id(entity.getPost().getId());
//		return dto;
//	}

    public static Image _toModel(ImageDTO dto) {
		Image model = new Image();
		model.setId(dto.getId());
		model.setName(dto.getName());
		model.setData(dto.getData());
		//dto.setPost(PostMapper._toDTO(entity.getPost()));
		model.setPost_id(dto.getPost_id());
		return model;
    }
}
