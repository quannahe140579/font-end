package com.example.instademo.mapper;

import com.example.instademo.dto.AnnounceDTO;
import com.example.instademo.dto.FriendDTO;
import com.example.instademo.dto.PostDTO;
import com.example.instademo.dto.UserDTO;
import com.example.instademo.model.Announce;
import com.example.instademo.model.Friend;
import com.example.instademo.model.Post;
import com.example.instademo.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {
    public static User _toModel(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setCreatedDate(dto.getCreatedDate());
        user.setActive(dto.isActive());
        user.setPassword(dto.getPassword());
        user.setUsername(dto.getUsername());
        user.setAddress(dto.getAddress());
        user.setAvatar(dto.getAvatar());
        user.setDateOfBirth(dto.getDateOfBirth());
        user.setFullName(dto.getFullName());
        user.setPhone(dto.getPhone());
        user.setListPos(_toListPost(dto.getListPos()));
        user.setListFriend(_toListFriend(dto.getListFriend()));
        user.setListAnnounce(_toListAnnounce(dto.getListAnnounce()));
        return user;
    }

    private static List<Announce> _toListAnnounce(List<AnnounceDTO> listAnnounceDTO) {
        List<Announce> listAnnounce = new ArrayList<>();
        if (listAnnounceDTO != null) {
            for (AnnounceDTO dto : listAnnounceDTO) {
                listAnnounce.add(AnnounceMapper._toModel(dto));
            }
        }
        return listAnnounce;
    }

    private static List<Post> _toListPost(List<PostDTO> listPosDto) {
        List<Post> listPost = new ArrayList<>();
        if (listPosDto != null) {
            for (PostDTO postDto : listPosDto
            ) {
                listPost.add(PostMapper._toModel(postDto));
            }
        }
        return listPost;
    }

    private static List<Friend> _toListFriend(List<FriendDTO> listFriendDTO) {
        List<Friend> listPost = new ArrayList<>();
        if (listFriendDTO != null) {
            for (FriendDTO friendDTO : listFriendDTO
            ) {
                listPost.add(FriendMapper._toModel(friendDTO));
            }
        }
        return listPost;
    }

    public static UserDTO _toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setCreatedDate(user.getCreatedDate());
        dto.setActive(user.isActive());
        dto.setPassword(user.getPassword());
        dto.setUsername(user.getUsername());
        dto.setAddress(user.getAddress());
        dto.setAvatar(user.getAvatar());
        dto.setDateOfBirth(user.getDateOfBirth());
        dto.setFullName(user.getFullName());
        dto.setPhone(user.getPhone());

        dto.setListPos(_toListPostDTO(user.getListPos()));
        dto.setListFriend(_toListFriendDTO(user.getListFriend()));
        dto.setListAnnounce(_toListAnnounceDTO(user.getListAnnounce()));
        return dto;
    }

    private static List<FriendDTO> _toListFriendDTO(List<Friend> listFriend) {
        List<FriendDTO> listPost = new ArrayList<>();
        if (listFriend != null) {
            for (Friend friendDTO : listFriend
            ) {
                listPost.add(FriendMapper._toDTO(friendDTO));
            }
        }
        return listPost;
    }

    private static List<AnnounceDTO> _toListAnnounceDTO(List<Announce> listAnnounce) {
        List<AnnounceDTO> listAnnounceDTO = new ArrayList<>();
        if (listAnnounce != null) {
            for (Announce model : listAnnounce) {
                listAnnounceDTO.add(AnnounceMapper._toDTO(model));
            }
        }
        return listAnnounceDTO;
    }

    private static List<PostDTO> _toListPostDTO(List<Post> listPos) {
        List<PostDTO> listPost = new ArrayList<>();
        if (listPos != null) {
            for (Post post : listPos
            ) {
                listPost.add(PostMapper._toDTO(post));
            }
        }
        return listPost;
    }

}
