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
import android.widget.TextView;
import android.widget.Toast;

import com.example.instademo.MainActivity;
import com.example.instademo.R;
import com.example.instademo.model.Post;

import java.text.SimpleDateFormat;
import java.util.List;

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
            viewHolder.tvContent = convertView.findViewById(R.id.txtDes);
            viewHolder.tvComment = convertView.findViewById(R.id.txtComment);
            viewHolder.imgLike = convertView.findViewById(R.id.img_like);
            viewHolder.tvTotalLike = convertView.findViewById(R.id.txtTotalLike);
            viewHolder.tvPublisher = convertView.findViewById(R.id.txtPublisher);
            viewHolder.imgComment = convertView.findViewById(R.id.img_comment);
            viewHolder.imgSave = convertView.findViewById(R.id.img_save);
            viewHolder.tvDate = convertView.findViewById(R.id.txtDate);
            viewHolder.tvUsername = convertView.findViewById(R.id.tvUserName);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Post post = listPost.get(position);

        viewHolder.imgProfile.setImageResource(R.drawable.naq);
        viewHolder.imgPostImage.setImageResource(R.drawable.naq);
        viewHolder.tvContent.setText(post.getContent());
        if(post.getListComment() != null){
            if(post.getListComment().size() > 0){
                viewHolder.tvComment.setText(post.getListComment().get(0).getContent());
                viewHolder.tvPublisher.setText(post.getListComment().get(0).getFriendName());
            }
        }

        viewHolder.tvTotalLike.setText(post.getTotalLike() + "");

        viewHolder.tvDate.setText(sdf.format(post.getCreatedDate()));
        viewHolder.tvUsername.setText(post.getUserName());
        viewHolder.imgLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _clickImgLike();
            }
        });
        viewHolder.imgComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _clickImgLike();
            }
        });
        viewHolder.imgSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _clickImgLike();
            }
        });
        viewHolder.tvComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _clickImgLike();
            }
        });
        viewHolder.tvPublisher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _clickImgLike();
            }
        });
        return convertView;
    }

    private static class ViewHolder{
        ImageView imgProfile, imgPostImage, imgLike, imgComment, imgSave;
        TextView tvTotalLike, tvPublisher, tvContent, tvComment, tvDate, tvUsername;
    }
    private void _clickImgLike(){
        Log.i("Img","Clicked");
        Log.d("INFO","Live view clicked");
        System.out.println("asd");
    }
}
