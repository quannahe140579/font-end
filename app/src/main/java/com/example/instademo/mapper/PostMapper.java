package com.example.instademo.mapper;

import com.example.instademo.dto.CommentDTO;
import com.example.instademo.dto.ImageDTO;
import com.example.instademo.dto.PostDTO;
import com.example.instademo.model.Comment;
import com.example.instademo.model.Image;
import com.example.instademo.model.Post;

import java.util.ArrayList;
import java.util.List;


public class PostMapper {
//	public static PostDTO _toDTO(Post entity) {
//		PostDTO dto = new PostDTO();
//		dto.setActive(entity.isActive());
//		dto.setContent(entity.getContent());
//		dto.setCreatedDate(entity.getCreatedDate());
//		dto.setId(entity.getId());
//		dto.setListComment(CollectionUtils.isEmpty(entity.getListComment()) ? null
//				: entity.getListComment().stream().map(c -> CommentMapper._toDTO(c)).collect(Collectors.toList()));
//		dto.setListImage(CollectionUtils.isEmpty(entity.getListImage()) ? null
//				: entity.getListImage().stream().map(i -> ImageMapper._toDTO(i)).collect(Collectors.toList()));
//		dto.setTotalLike(entity.getTotalLike());
//		dto.setUser_id(entity.getUser().getId());
//		return dto;
//	}

    public static Post _toModel(PostDTO dto) {
        Post model = new Post();
        model.setActive(dto.isActive());
        model.setContent(dto.getContent());
        model.setCreatedDate(dto.getCreatedDate());
        model.setId(dto.getId());
        model.setListComment(_toListComment(dto.getListComment()));
        model.setListImage(_toListImage(dto.getListImage()));
        model.setUser_id(dto.getUser_id());
        return model;
    }

    private static List<Comment> _toListComment(List<CommentDTO> listDto) {
        List<Comment> listComment = new ArrayList<>();
        if (listDto != null) {
            for (CommentDTO c : listDto
            ) {
                listComment.add(CommentMapper._toModel(c));
            }
        }
        return listComment;
    }

    private static List<Image> _toListImage(List<ImageDTO> listDto) {
        List<Image> listImage = new ArrayList<>();
        if (listDto != null) {
            for (ImageDTO c : listDto
            ) {
                listImage.add(ImageMapper._toModel(c));
            }
        }

        return listImage;
    }
}
