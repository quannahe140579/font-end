package com.example.instademo.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.instademo.R;
import com.example.instademo.model.Comment;
import com.example.instademo.utils.LocalConst;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CommentAdapterLv extends BaseAdapter {
    Activity activity;
    List<Comment> list;

    public CommentAdapterLv(Activity activity, List<Comment> list) {
        this.activity = activity;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = activity.getLayoutInflater().inflate(R.layout.comment_layout_1,null);
            viewHolder = new ViewHolder();

            viewHolder.tvUsername = convertView.findViewById(R.id.txtUserNameCmt);
            viewHolder.tvContent = convertView.findViewById(R.id.txtCommentCmt);
            viewHolder.imgProfile = convertView.findViewById(R.id.image_profile_cmt);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Comment c = list.get(position);

        viewHolder.tvUsername.setText(c.getFriendName());
        viewHolder.tvContent.setText(c.getContent());
        Picasso.with(activity).load(LocalConst.URL + "/uploads/" + c.getFriendName() + ".jpg")
                .error(R.drawable.icon_defaul_account)
                .into(viewHolder.imgProfile);
        return convertView;
    }
    private static class ViewHolder{
        TextView tvUsername, tvContent;
        ImageView imgProfile;
    }
}
