package com.example.instademo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instademo.R;
import com.example.instademo.model.Post;

import java.util.List;

public class CustomePostAdapter extends RecyclerView.Adapter<CustomePostAdapter.PostViewHolder>{
    private Context mContext;
    private List<Post> listPost;


    public CustomePostAdapter(Context mContext) {
        this.mContext = mContext;
    }
    public void setData(List<Post> list){
        this.listPost = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item,parent,false);

        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = listPost.get(position);
        if(post != null){
            ImageView imgProfile = holder.imgProfile;
            imgProfile.setImageResource(R.drawable.naq);

            ImageView imgPostImage = holder.imgPostImage;
            imgPostImage.setImageResource(R.drawable.naq);

            TextView tvTotalLike = holder.tvTotalLike;
            tvTotalLike.setText(post.getTotalLike() + "");

            TextView tvPublisher = holder.tvPublisher;
            tvPublisher.setText("User " + post.getUser_id() + "");

            TextView tvContent = holder.tvContent;
            tvContent.setText(post.getContent());

            TextView tvViewAll = holder.tvViewAll;
            tvViewAll.setText("View all " + post.getListComment().size() + " comments ");

            TextView tvUsername = holder.tvUsername;
            tvUsername.setText(post.getUserName());
        }

    }

    @Override
    public int getItemCount() {
        if(listPost != null){
            return listPost.size();
        }
        return 0;
    }

    public class PostViewHolder extends RecyclerView.ViewHolder{
        ImageView imgProfile, imgPostImage, imgLike, imgComment, imgSave;
        TextView tvTotalLike, tvPublisher, tvContent, tvViewAll, tvUsername;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProfile = itemView.findViewById(R.id.img_profile);
            imgPostImage = itemView.findViewById(R.id.img_post1);
            imgLike = itemView.findViewById(R.id.img_like);
            imgComment = itemView.findViewById(R.id.img_comment);
            imgSave = itemView.findViewById(R.id.img_save);

            tvTotalLike = itemView.findViewById(R.id.txtTotalLike);
            tvPublisher = itemView.findViewById(R.id.txtPublisher);
            tvContent = itemView.findViewById(R.id.txtDes);
            tvViewAll = itemView.findViewById(R.id.txtComment);
            tvUsername = itemView.findViewById(R.id.tvUserName);
        }
    }

}
