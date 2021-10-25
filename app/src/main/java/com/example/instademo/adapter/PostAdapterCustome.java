package com.example.instademo.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instademo.MainActivity;
import com.example.instademo.R;
import com.example.instademo.api.ApiService;
import com.example.instademo.model.Post;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostAdapterCustome extends BaseAdapter {
    private Activity activity;
    private List<Post> listPost;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");

    public PostAdapterCustome(Activity activity, List<Post> listPost) {
        this.activity = activity;
        this.listPost = listPost;
    }

    @Override
    public int getCount() {
        if(listPost != null){
            return  listPost.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return listPost.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = activity.getLayoutInflater().inflate(R.layout.post_item,null);
            viewHolder = new ViewHolder();

            viewHolder.imgProfile = convertView.findViewById(R.id.img_profile);
            viewHolder.imgPostImage = convertView.findViewById(R.id.img_post1);
            viewHolder.imgLike = convertView.findViewById(R.id.img_like);
            viewHolder.imgLike.setTag(R.id.img_like);
            viewHolder.tvTotalLike = convertView.findViewById(R.id.txtTotalLike);
            viewHolder.imgComment = convertView.findViewById(R.id.img_comment);
            viewHolder.imgSave = convertView.findViewById(R.id.img_save);
            viewHolder.tvDate = convertView.findViewById(R.id.txtDate);
            viewHolder.tvUsername = convertView.findViewById(R.id.tvUserName);
            viewHolder.tvViewAll = convertView.findViewById(R.id.tvViewall);
            viewHolder.tvViewAll.setTag("NON_CLICK");
            viewHolder.lvComment = convertView.findViewById(R.id.rc_comment);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Post post = listPost.get(position);

        viewHolder.imgProfile.setImageResource(R.drawable.naq);

        Picasso.with(activity).load("http://192.168.1.5:8080/uploads/" + post.getUrlAvt())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.drawable.img_default)
                .into(viewHolder.imgProfile);

        viewHolder.imgPostImage.setImageResource(R.drawable.naq);
        Picasso.with(activity).load("http://192.168.1.5:8080/uploads/" + post.getListImage().get(0).getName())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.drawable.img_default)
                .into(viewHolder.imgPostImage);


        viewHolder.tvTotalLike.setText(post.getTotalLike() + "");

        viewHolder.tvDate.setText(sdf.format(post.getCreatedDate()));
        viewHolder.tvUsername.setText(post.getUserName());
        viewHolder.imgLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _clickImgLike(post.getId(), viewHolder.imgLike, viewHolder.tvTotalLike);
            }
        });
        viewHolder.imgComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        viewHolder.tvViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewHolder.tvViewAll.getTag().equals("NON_CLICK")){
                    if(post.getListComment() != null){
                        CommentAdapterLv adapter = new CommentAdapterLv(activity,post.getListComment());
                        viewHolder.lvComment.setAdapter(adapter);
                        viewHolder.tvViewAll.setTag("CLICKED");
                    }
                }else{
                    viewHolder.lvComment.setAdapter(null);
                    viewHolder.tvViewAll.setTag("NON_CLICK");
                }

            }
        });
        return convertView;
    }

    private static class ViewHolder{
        ImageView imgProfile, imgPostImage, imgLike, imgComment, imgSave;
        TextView tvTotalLike, tvComment, tvDate, tvUsername, tvViewAll;
        ListView lvComment;
    }
    private void _clickImgLike(long postId, ImageView imgLike, TextView tvLike){
        int id_imageLike = ((int)imgLike.getTag());
        int type = 0;
        if(id_imageLike == R.id.img_like){
            type = 1;
            imgLike.setImageResource(R.drawable.ic_liked);
            imgLike.setTag(R.drawable.ic_liked);
        }else{
            type = 0;
            imgLike.setImageResource(R.drawable.ic_like);
            imgLike.setTag(R.drawable.ic_like);
        }
        ApiService.apiService.likePost(postId,type).enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                Long totolLike = response.body();
                tvLike.setText(totolLike + "");
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {

            }
        });
    }
}
