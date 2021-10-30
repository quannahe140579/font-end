package com.example.instademo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.instademo.R;
import com.example.instademo.adapter.CommentAdapterLv;
import com.example.instademo.api.ApiService;
import com.example.instademo.dto.CommentDTO;
import com.example.instademo.model.Announce;
import com.example.instademo.model.Comment;
import com.example.instademo.model.Post;
import com.example.instademo.utils.LocalConst;
import com.example.instademo.utils.LogedUser;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Optional;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentActivity extends AppCompatActivity {
    private ListView lvCmt;
    private ImageView imgProfile;
    private TextView tvAdd;
    private EditText etAddCmt;
    private List<Comment> listCmt;
    private Post post;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        initComponent();

        long id = getIntent().getLongExtra("id",-1);
        if(id != -1){
            loadData(id);
        }
        setOnClick();
    }
    private void setOnClick(){
        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!etAddCmt.getText().toString().trim().equals("")){
                    String content = etAddCmt.getText().toString().trim();
                    Comment comment = new Comment();
                    comment.setFriendName(LogedUser.logedUser.getUsername());
                    comment.setPost_id(post.getId());
                    comment.setContent(content);

                    listCmt.add(comment);

                    CommentDTO dto = new CommentDTO();
                    dto.setFriendName(comment.getFriendName());
                    dto.setPost_id(post.getId());
                    dto.setContent(content);
                    dto.setFriend_id(LogedUser.logedUser.getId());

                    ApiService.apiService.sendComment(dto).enqueue(new Callback<Optional>() {
                        @Override
                        public void onResponse(Call<Optional> call, Response<Optional> response) {

                        }

                        @Override
                        public void onFailure(Call<Optional> call, Throwable t) {

                        }
                    });
                    etAddCmt.setText("");
                    CommentAdapterLv adapterLv = new CommentAdapterLv(CommentActivity.this,listCmt);
                    lvCmt.setAdapter(adapterLv);
                    adapterLv.notifyDataSetChanged();

                    Announce announce = new Announce();
                    announce.setTitle(LogedUser.logedUser.getUsername() + " da binh luan ve anh cua ban");
                    announce.setUser_id(post.getUser_id());
                    announce.setFriend_id(LogedUser.logedUser.getId());
                    announce.setPost_id(post.getId());

                    ApiService.apiService.sendAnnouce(announce).enqueue(new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) {

                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {

                        }
                    });
                }
            }
        });
    }
    private void loadData(long id){
        Picasso.with(this).load(LocalConst.URL + "/uploads/" + LogedUser.logedUser.getAvatar())
        .into(imgProfile);
        getListComment(id);
        CommentAdapterLv adapterLv = new CommentAdapterLv(this,listCmt);
        lvCmt.setAdapter(adapterLv);
        adapterLv.notifyDataSetChanged();
    }
    private void initComponent(){
        lvCmt = findViewById(R.id.lv_listFriend);
        imgProfile = findViewById(R.id.img_post_add);
        tvAdd = findViewById(R.id.tv_post_cmt);
        etAddCmt = findViewById(R.id.et_add_comment);
    }
    private void getListComment(long id){
        for (Post p: LogedUser.listPost
             ) {
            if(p.getId() == id){
                post = p;
                listCmt = p.getListComment();
            }
        }
    }
}