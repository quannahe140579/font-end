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
        model.setUserName(dto.getUserName());
        model.setTotalLike(dto.getTotalLike());
        model.setUrlAvt(dto.getUrlAvt());
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

    public static List<Post> _toListModel(List<PostDTO> listDTO) {
        List<Post> listResult = new ArrayList<>();
        for (PostDTO dtp: listDTO
             ) {
            listResult.add(_toModel(dtp));
        }
        return listResult;
    }

    public static PostDTO _toDTO(Post post) {
        PostDTO dto = new PostDTO();
        dto.setActive(post.isActive());
        dto.setContent(post.getContent());
        dto.setCreatedDate(post.getCreatedDate());
        dto.setId(post.getId());
        dto.setListComment(_toListCommentDTO(post.getListComment()));
        dto.setListImage(_toListImageDTO(post.getListImage()));
        dto.setUser_id(post.getUser_id());
        dto.setUserName(post.getUserName());
        dto.setTotalLike(post.getTotalLike());
        dto.setUrlAvt(post.getUrlAvt());
        return dto;
    }

    private static List<ImageDTO> _toListImageDTO(List<Image> listImage) {
        List<ImageDTO> listImageDTO = new ArrayList<>();
        if (listImage != null) {
            for (Image i : listImage
            ) {
                listImageDTO.add(ImageMapper._toDTO(i));
            }
        }

        return listImageDTO;
    }

    private static List<CommentDTO> _toListCommentDTO(List<Comment> listComment) {
        List<CommentDTO> listCommentDTO = new ArrayList<>();
        if (listComment != null) {
            for (Comment c : listComment
            ) {
                listCommentDTO.add(CommentMapper._toDTO(c));
            }
        }
        return listCommentDTO;
    }
}
