package com.example.instademo.api;

import android.graphics.Path;

import com.example.instademo.dto.AnnounceDTO;
import com.example.instademo.dto.CommentDTO;
import com.example.instademo.dto.FriendDTO;
import com.example.instademo.dto.PostDTO;
import com.example.instademo.dto.UserDTO;
import com.example.instademo.model.Announce;
import com.example.instademo.model.Comment;
import com.example.instademo.model.LoginForm;
import com.example.instademo.model.PostForm;
import com.example.instademo.model.RegisterForm;
import com.example.instademo.model.UserForm;
import com.example.instademo.utils.LocalConst;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.Optional;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ApiService {
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    ApiService apiService = new Retrofit.Builder()
            .baseUrl(LocalConst.URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @POST("/api/users/login")
    Call<UserDTO> getUser(@Body LoginForm form);

    @POST("/api/users/register")
    Call<UserDTO> register(@Body RegisterForm form);

    @GET("/api/post")
    Call<List<PostDTO>> getAllPost(@Query("id") long userId);

    @POST("/api/users/profile")
    Call<UserDTO> updateUser(@Body UserForm form);

    @PUT("/api/post/like")
    Call<Long> likePost(@Query("postId") long postId, @Query("type") int type);

    @POST("/api/announce")
    Call<Optional> sendAnnouce(@Body Announce announce);

    @POST("/api/comment")
    Call<Optional> sendComment(@Body CommentDTO dto);

    @GET("/api/friend")
    Call<List<FriendDTO>> findFriend(@Query("name") String name);

    @POST("/api/friend")
    Call<Integer> addFriend(@Query("user_id") long user_id,@Query("friend_id") long friend_id);

    @PUT("/api/friend")
    Call<Integer> removeFriend(@Query("user_id") long user_id,@Query("friend_id") long friend_id);

    @PUT("/api/announce")
    Call<Integer> deleteAnnounce(@Query("user_id") long user_id,@Query("friend_id") long friend_id);

    @POST("/api/post/create")
    Call<PostDTO> createPost(@Body PostForm form);

    @GET("/api/users")
    Call<UserDTO> getUserByUsername(@Query("username") String username);

    @PUT("/api/post/delete")
    Call<Optional> deletePost(@Query("postId") long postId);
}
