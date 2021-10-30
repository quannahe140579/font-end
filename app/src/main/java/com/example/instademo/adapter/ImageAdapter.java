package com.example.instademo.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.instademo.R;
import com.example.instademo.activity.FullScreenActivity;
import com.example.instademo.utils.LocalConst;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends BaseAdapter {
    Context context;
    List<String> listImg;
    boolean isImageFitToScreen;

    public ImageAdapter(Context context, List<String> listImg) {
        this.context = context;
        this.listImg = listImg;
    }

    @Override
    public int getCount() {
        return listImg.size();
    }

    @Override
    public Object getItem(int i) {
        return listImg.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView ==  null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.image_list, parent, false);
        }
        ImageView imageView;
        imageView = (ImageView) convertView.findViewById(R.id.image);
        Picasso.with(context).load(LocalConst.URL + "/uploads/" + listImg.get(position))
                .error(R.mipmap.ic_launcher)
                .into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FullScreenActivity.class);
                intent.putExtra("url",listImg.get(position));
                context.startActivity(intent);
            }
        });


        return convertView;
    }


}
