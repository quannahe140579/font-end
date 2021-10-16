package com.example.instademo.mapper;

import com.example.instademo.dto.ImageDTO;
import com.example.instademo.model.Image;


public class ImageMapper {

    public static Image _toModel(ImageDTO dto) {
		Image model = new Image();
		model.setId(dto.getId());
		model.setName(dto.getName());
		model.setPost_id(dto.getPost_id());
		return model;
    }

    public static ImageDTO _toDTO(Image i) {
		ImageDTO dto = new ImageDTO();
		dto.setId(i.getId());
		dto.setName(i.getName());
		dto.setPost_id(i.getPost_id());
		return dto;
    }
}
