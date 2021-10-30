package com.example.instademo.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.instademo.R;
import com.example.instademo.model.Announce;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

public class NotifyAdapter extends BaseAdapter {
    Activity activity;
    List<Announce> list;

    public NotifyAdapter(Activity activity, List<Announce> list) {
        this.activity = activity;
        this.list = list;
        Collections.reverse(this.list);
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
            convertView = activity.getLayoutInflater().inflate(R.layout.notification_item_layout,null);
            viewHolder = new ViewHolder();

            viewHolder.imgProfile = convertView.findViewById(R.id.img_post_add);
            viewHolder.tvNotify = convertView.findViewById(R.id.tv_username);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Announce notification = list.get(position);
        viewHolder.tvNotify.setText(notification.getTitle());
        Picasso.with(activity).load(R.drawable.notification_icon).into(viewHolder.imgProfile);
        return convertView;
    }
    private static class ViewHolder{
        TextView tvNotify;
        ImageView imgProfile;
    }
}
